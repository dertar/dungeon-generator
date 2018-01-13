package com.t3o.map;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.t3o.assets.Assets;
import com.t3o.assets.resources.DecorationResource;
import com.t3o.assets.resources.PatternBlockResource;
import com.t3o.assets.resources.StaticDecorationResource;
import com.t3o.map.generators.CaveGenerator;
import com.t3o.assets.resources.TileResource;
import com.t3o.map.generators.TemplateGenerator;
import com.t3o.screens.Ratio;
import com.t3o.utils.*;
import jdk.nashorn.internal.runtime.regexp.joni.CodeRangeBuffer;

import java.util.Iterator;


public class WorldMap {
    public static final boolean BEMPTY = false;
    public static final boolean BBLOCKED = true;

    static public final int BLOCKED = -1;
    static public final int EMPTY = 0;
    static public final int CAP = -99;

    public String background[][];
    public Tile map[][];


    Assets assets;
    TemplateGenerator.ROOM[][] rooms;

    public Array<Coordinates> emptyCells;
    public Array<Coordinates> decorFloor;
    public Array<Coordinates> decorRoof;

    public Array<Coordinates> inputs;

    public Array<DecorationStruct> decorationStructs;

    public Array<ShapeRect> bodyRects;
    public Array<Coordinates> edges;


    Array<Coordinates> ligths;

    ArrayMap<Integer,Array<Integer>> decorsWeightIDs;

    //  asdasdafsdfgsdfgsdf!!!!
    int widthRoom;
    int heightRoom;


    public WorldMap(final Assets assets){
        this.assets = assets;
        getSizeOfRoom();
        inputs = new Array<Coordinates>();
        decorFloor = new Array<Coordinates>();
        emptyCells = new Array<Coordinates>();

        decorationStructs = new Array<DecorationStruct>();
        ligths = new Array<Coordinates>();
        decorRoof = new Array<Coordinates>();

        initDecorTiles();
    }



    public int getWidth(){
        return map.length;
    }

    public int getHeight(){
        return map[0].length;
    }

    public void generate(){
        rooms = TemplateGenerator.generate(6,4,0.5f);

        for(int i = 0; i < rooms[0].length; i++){
            for(int j = 0; j < rooms.length; j++){
                System.out.print(rooms[j][i] + " ");
            }
            System.out.println();
        }

        genMap();


        genTiles();



        findEdges();

        bodyRects = ShapeBoxBuilder.makeRects(edges,map,assets);


        boolean[][] bmap = getBooleanMap();

        findDecorationCells(bmap);

        boolean [][]bbg = CaveGenerator.generate(57,28, CaveGenerator.CAVE.NORMAL);



        genDecorations(bmap,bbg,(int)(decorFloor.size * 0.15),(int)(decorFloor.size * 0.15),(int)(decorRoof.size * 0.15f),0.45f);

        genBackground(bbg);

        genDecorTiles(bmap);
    }

    private Vector2 rndPlaceForDecorations(int mX, int mY,float width,float offsetX,float offsetY,boolean isMirrored){
        if(width > Ratio.sizeTile){
            Gdx.app.error("WorldMap.rndPlaceForDecor","width of tile bigger then tile's width");
        }
        if(width == 0){
            Gdx.app.error("WorldMap.rndPlaceForDecor","width == 0");

        }
        //isMirrored = false;
        Vector2 ret = new Vector2(  mX * Ratio.sizeTile, mY * Ratio.sizeTile  );

        float offset =  width + offsetX >= Ratio.sizeTile ? 0 : MathUtils.random(0,Ratio.sizeTile - width - offsetX);


        //offset = 0;

        ret.x += offset;


        ret.y += offsetY;

        return ret;
    }

    private void genDecorations(boolean[][] bmap,boolean[][] bbg,int quantityBG,int quantity,int quantityRoof,float chanceStatic){


        // lights
        Array<Integer> indexOfTorch = assets.getIndexOfLights();
        Array<Integer> approachedLights = new Array<Integer>();

        for(Coordinates light : ligths){
            for(int i = 0; i < indexOfTorch.size; i++){
                DecorationResource decor = assets.getDecor(indexOfTorch.get(i));
                if( decor.pattern != null && decor.pattern.isApproach(bmap,light.x - 1,light.y + 1)){
                    approachedLights.add(indexOfTorch.get(i));
                }
            }
            if(approachedLights.size != 0) {
                int rndTorch = approachedLights.size >= 2 ? MathUtils.random(0, approachedLights.size - 1) : 0;

                DecorationResource decor = assets.getDecor( approachedLights.get(rndTorch) );

                int x = light.x,
                        y = light.y;

                if(decor.offsetPattern != null){
                    x += decor.offsetPattern.y;
                    y += decor.offsetPattern.x;
                }

                if(decor.isOnBg){
                    float xB = x  / Ratio.sizeRatio,
                            yB = y  / Ratio.sizeRatio;


                    for(int i = (int)(xB - 1); i < (int)(xB + 2); i++){
                        for(int j = (int) (yB - 1); j < (int)(yB + 2); j++){
                            if(!Utils.isFringle(i,j,bbg.length,bbg[0].length)) {
                                bbg[i][j] = BBLOCKED;
                            }
                        }
                    }
                }

                DecorationStruct struct = genDynamicDecoration(decor,x,y,2f,false);


                decorationStructs.add(struct);
                approachedLights.clear();
            }
        }


        int statsStaticBG, statsStatic, statsDynamic;

        statsStatic = statsStaticBG = statsDynamic = 0;

        Array<Coordinates> tmpFloor = new Array<Coordinates>();
        tmpFloor.addAll(decorFloor);

        tmpFloor.removeAll(ligths,true);


        while(quantity-- > 0){
            statsStatic++;
            int rndIndex = MathUtils.random(0, tmpFloor.size - 1);

            Coordinates coord = tmpFloor.get(rndIndex);


            tmpFloor.removeIndex(rndIndex);

            DecorationStruct struct = genStaticDecoration(assets.rndStaticDecor(true),coord.x,coord.y,0.9f,false);

            decorationStructs.add(struct);
        }

        tmpFloor.clear();
        tmpFloor.addAll(decorFloor);

        while(quantityBG-- >= 0) {
            int rndIndex = MathUtils.random(0, tmpFloor.size - 1);

            Coordinates coord = tmpFloor.get(rndIndex);
            DecorationStruct struct;

            tmpFloor.removeIndex(rndIndex);

            if(MathUtils.random(0,1) >= chanceStatic) {
                struct = genStaticDecoration(assets.rndStaticDecor(false),coord.x,coord.y,2f,false);
                statsStaticBG++;
            }else{
                struct = genDynamicDecoration(assets.rndDecor(),coord.x,coord.y,2f,false);

                statsDynamic++;
            }

            decorationStructs.add(struct);
        }

        tmpFloor.clear();
        tmpFloor.addAll(decorRoof);

        while(quantityRoof-- > 0){
            statsStatic++;
            int rndIndex = MathUtils.random(0, tmpFloor.size - 1);

            Coordinates coord = tmpFloor.get(rndIndex);


            tmpFloor.removeIndex(rndIndex);

            DecorationStruct struct = MathUtils.random(0,9) == 0 ? genDynamicDecoration(assets.rndDynamicDecorRoof(),coord.x,coord.y ,1.1f,true)
                    : genStaticDecoration(assets.rndStaticDecorRoof(),coord.x,coord.y ,1.1f,true);

            decorationStructs.add(struct);
        }

        ligths.clear();
        Gdx.app.log("Map.genDecorations", "statsStaticBG: " + statsStaticBG + ", statsStatic: " + statsStatic + ", statsDynamic: " + statsDynamic);

    }



    DecorationStruct genStaticDecoration(StaticDecorationResource staticDecorationResource,int mX,int mY,float layer,boolean roof){

        DecorationStruct struct = new DecorationStruct();
        struct.isMirrored = false;
        struct.scale = MathUtils.random( staticDecorationResource.minScale, staticDecorationResource.maxScale );

        if(staticDecorationResource.canMirror)
            struct.isMirrored = MathUtils.randomBoolean();

//        struct.isMirrored = true;

        struct.name = staticDecorationResource.textureName;

        int width = assets.findTexture( staticDecorationResource.textureName ).getRegionWidth();

        struct.pos = rndPlaceForDecorations(mX,mY,width * struct.scale,staticDecorationResource.offset.x,staticDecorationResource.offset.y,struct.isMirrored);

        if(roof){
            int height = assets.findTexture( staticDecorationResource.textureName ).getRegionHeight();
            struct.pos.y += Ratio.sizeTile - height;
        }

        struct.layer = layer;


        struct.isStatic = true;

        return struct;
    }

    DecorationStruct genDynamicDecoration(DecorationResource animationsName,int mX,int mY,float layer,boolean roof){
        DecorationStruct struct = new DecorationStruct();

        struct.isMirrored = false;
        struct.scale = MathUtils.random( animationsName.minScale, animationsName.maxScale );

        if(animationsName.canMirror)
            struct.isMirrored = MathUtils.randomBoolean();

//        struct.isMirrored = false;

        struct.isStatic = false;

        struct.name = animationsName.animation.name;
        struct.delta = MathUtils.random(0f, assets.animations.get(animationsName.animation.name).getAnimationDuration());


        int width = assets.findTexture( animationsName.animation.name ).getRegionWidth(),
                height = assets.findTexture( animationsName.animation.name ).getRegionHeight();
        struct.pos = rndPlaceForDecorations(mX,mY,width * struct.scale,animationsName.offset.x,animationsName.offset.y,struct.isMirrored);
        if(roof){
            struct.pos.y += Ratio.sizeTile - height;
        }

        struct.layer = layer;


        if(animationsName.light != null && animationsName.color != null) {
            float correctX =  struct.isMirrored ? width - animationsName.light.x : animationsName.light.x,
                    correctY =  height - animationsName.light.y + animationsName.offset.y;

            struct.lightContainer = new LightContainer((int) (struct.pos.x + correctX),
                    (int) (struct.pos.y + correctY),
                    animationsName.color, animationsName.pxDistLightMin, animationsName.pxDistLightMax, animationsName.rays);
        }
        return struct;
    }

    void genDecorTiles(boolean [][]bmap){


        Array<Integer> keys = decorsWeightIDs.keys().toArray();

        keys.sort();

        Array<Coordinates> filled = new Array<Coordinates>();

        Gdx.app.log(decorsWeightIDs.toString(),keys.toString());


        for(int i = keys.size - 1; i >= 0; i--) {
            genDecorTilesAtMapById(bmap, decorsWeightIDs.get(keys.get(i)),filled);
        }

        //genDecorTilesAtMapById(bmap, decorsWeightIDs.get(4),filled);

    }

    Array<Coordinates> fillDecorTiles(final boolean[][] bmap, TileResource tile, Array<Coordinates> globalFilled){
        Array<Coordinates> filled = new Array<Coordinates>();
        Array<Coordinates> ret = new Array<Coordinates>();


        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                Coordinates floor = new Coordinates(i, j);

                if (!Utils.isContains(filled, floor) && !Utils.isContains(globalFilled, floor) && tile.pattern.isApproach(bmap, floor.x - 1, floor.y + 1)) {
                    ret.add(floor);

                    for (int cJ = 0; cJ < tile.textureName.length; cJ++) {
                        for (int cI = 0; cI < tile.textureName[0].length; cI++) {
                            filled.add(new Coordinates(floor.x + cI, floor.y + cJ ));
                        }
                    }

                }
            }
        }

        filled.clear();
        return ret;
    }


    void genDecorTilesAtMapById(boolean [][]bmap, Array<Integer> ids, Array<Coordinates> globalFilled){
        TileResource[] tileResources = assets.getTiles();

        ArrayMap<Integer, Array<Coordinates> > canPut = new ArrayMap<Integer, Array<Coordinates>>();

        for(Integer i : ids){
            canPut.put(i, fillDecorTiles(bmap,tileResources[i], globalFilled));
        }

        for(Iterator<ObjectMap.Entry<Integer,Array<Coordinates>>> it = canPut.iterator(); it.hasNext(); ){
            ObjectMap.Entry<Integer,Array<Coordinates>> entry = it.next();


            Array<Coordinates> coordinates = entry.value;
            int idResource = entry.key;


            int quantity = (int) (coordinates.size * tileResources[idResource].chance);
            Gdx.app.log("Map.genDecorTilesAtMapById " + idResource, "quantity: " + quantity);
            if (quantity != 0) {
                while (quantity-- > 0) {



                    Coordinates coord = coordinates.random();

                    coordinates.removeValue(coord,true);


                    //filled.addAll(  );
                    Array<Coordinates> chunk = setChunkTile(tileResources[idResource], coord.x, coord.y  /*- 1*/);
                    if (chunk != null) {
                        globalFilled.addAll(chunk);
                    }
                }
            }
        }







        canPut.clear();


        //return filled;
    }



    void initDecorTiles(){
        decorsWeightIDs = new ArrayMap<Integer, Array<Integer>>();

        TileResource tiles[] = assets.getTiles();


        for(int i = 0; i < tiles.length; i++){
            if(tiles[i].isDecor && !tiles[i].isBg) {
                int weight = tiles[i].getWeight();
                if (!decorsWeightIDs.containsKey(weight)) {
                    decorsWeightIDs.put(weight, new Array<Integer>());
                }
                decorsWeightIDs.get(weight).add(i);
            }
        }

    }


    void findEdges(){
        edges = new Array<Coordinates>();

        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j].getBool() == BEMPTY) {
                    Array<Coordinates> neighbors = getXYNeighbors(i, j, true);
                    removeSpecificCells(neighbors,BEMPTY);

                    neighbors.removeAll(edges,true);

                    edges.addAll(neighbors);
                }
            }
        }
    }

    void removeSpecificCells(Array<Coordinates> list,boolean cell){
        for(Iterator<Coordinates> it = list.iterator(); it.hasNext(); ){
            Coordinates c = it.next();
            if(map[c.x][c.y].getBool() == cell) it.remove();
        }
    }

    /* if all == false

           * 1 *
           2 * 3
           * 4 *
      else all == true
           1 2 3
           4 * 5
           6 7 8
     */

    public Array<Coordinates> getXYNeighbors(int x, int y, boolean all){
        return getXYNeighbors(x,y,map.length,map[0].length,all);
    }


    static public Array<Coordinates> getXYNeighbors(int x, int y,int width,int height, boolean all){
        Array<Coordinates> ret = new Array<Coordinates>();

        Coordinates tmp = null;
        int []indexes;
        if(all) indexes = new int[]{0,1,2,3,4,5,6,7,8};
        else indexes = new int[]{1,3,4,6};

        for(int i = 0; i < indexes.length; i++){
            tmp = getCoordNeighbor(x,y,width, height,indexes[i]);
            if(tmp != null) ret.add(tmp);
        }

        return ret;
    }

    static Coordinates getCoordNeighbor(int x,int y,int width,int height,int dir){

        int     nX = getXDir(x,dir),
                nY = getYDir(y,dir);

        if(Utils.isFringle(nX,nY,width,height))
            return null;

        return new Coordinates(nX,nY);
    }

    void getSizeOfRoom(){
        if(assets.roomPatterns.containsKey(TemplateGenerator.ROOM.LEFT_RIGHT)) {
            widthRoom = assets.roomPatterns.get(TemplateGenerator.ROOM.LEFT_RIGHT).get(0).length;
            heightRoom = assets.roomPatterns.get(TemplateGenerator.ROOM.LEFT_RIGHT).get(0)[0].length;
        }else Gdx.app.error("Map.getSizeOfRoom","Some problems with roomPatterns");
    }

    void genMap(){
        inputs.clear();
        decorationStructs.clear();
        ligths.clear();

        map = new Tile[ heightRoom * rooms.length + 2 ][ widthRoom * rooms[0].length + 2];
        for(int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = new Tile();
            }
        }

        Gdx.app.log("Map.genMap","w: " + map.length + ", h: " + map[0].length );

        for(int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[0].length; j++) {
                genRoom(i * heightRoom + 1 , (rooms[0].length  - j - 1) * widthRoom + 1,rooms[i][j],j);
            }
        }

        puffins();
        Gdx.app.log("Map.genMap","end" );
    }

    void puffins(){
        for(int i = 0; i < map.length; i++) {
            map[i][0].setBool(BBLOCKED);
            map[i][map[0].length - 1].setBool(BBLOCKED);
        }

        for(int i = 0; i < map[0].length; i++) {
            map[0][i].setBool(BBLOCKED);
            map[map.length - 1][i].setBool(BBLOCKED);
        }

    }

    void genRoom(int x, int y, TemplateGenerator.ROOM room, int floor){
        Gdx.app.log("WorldMap","genRoom: " + room.toString());
        if(room.equals(TemplateGenerator.ROOM.TREASURE)){
            for(int i = x; i < x + heightRoom; i++) {
                for (int j = y; j < y + widthRoom; j++) {
                    map[i][j].setBool( true );
                }
            }
        }else if(room.equals(TemplateGenerator.ROOM.EMPTY)){
            for(int i = x; i < x + heightRoom; i++) {
                for (int j = y; j < y + widthRoom; j++) {
                    map[i][j].setBool( true );
                }
            }

        }else if(room.equals(TemplateGenerator.ROOM.CAVE)){
            int countRooms = 0,chance = 0;
            boolean[][] cave;
            Array<Coordinates> ends = new Array<Coordinates>();

            do {
                cave = CaveGenerator.generate(widthRoom, heightRoom, CaveGenerator.CAVE.NORMAL, 0.7f);


                countRooms = Utils.max(rooms(cave));

            }while(countRooms > 1 && chance++ < 20);

            Gdx.app.log("WorldMap","cave done");
            for(int i = 0; i < cave.length; i++){
                for(int j = 0; j < cave[0].length; j++){
                    Array<Coordinates> neighbors = getXYNeighbors(i,j,cave.length,cave[0].length,true);
                    boolean add = true;
                    for(Coordinates coordinate : neighbors){
                        if(cave[coordinate.x][coordinate.y] == BBLOCKED){
                            add = false;
                            break;
                        }
                    }
                    if(add){
                        ends.add(new Coordinates(i,j));
                    }
                }
            }

            Gdx.app.log("WorldMap","cave path done");
            if(ends.size != 0){

//                for(int i = 0; i < cave.length; i++) {
//                    cave[i][cave[0].length - 1] = BEMPTY; //right
//                    cave[i][0] = BEMPTY;
//                }




                Coordinates startLeft = new Coordinates(3,0),
                            startRight = new Coordinates(3,heightRoom),
                            endLeft = ends.get(MathUtils.random(0,ends.size - 1)),
                            endRight = ends.get(MathUtils.random(0,ends.size - 1));

                Gdx.app.log("coords", startLeft.toString() + "->" + endLeft.toString() + ":" + startRight.toString() + "->" + endRight.toString());

                //Gdx.app.log("pathg",path.toString());
                Array<Coordinates> leftPath = lee(cave,startLeft, endLeft, true, false),
                        rightPath = lee(cave,startRight, endRight, true, false);

                if(leftPath.size >= 1)
                    makeTunnel(cave,leftPath);


                if(rightPath.size >= 1)
                    makeTunnel(cave,rightPath);
                //Gdx.app.log("2",Utils.toString(Utils.wrap(cave)));
            }
            Gdx.app.log("WorldMap","tunnel done");
            for(int i = x; i < x + heightRoom; i++) {
                for (int j = y; j < y + widthRoom; j++) {

                    map[i][j].setBool(cave[j - y][i - x]);

                }
            }

        }else {
            Array<PatternBlockResource[][]> patterns = assets.roomPatterns.get(room);
            if (patterns == null || patterns.size == 0) {
                Gdx.app.error("Map.genRoom", "patterns are empty or null");
            }

            int rolledPattern = MathUtils.random(0, patterns.size - 1);

            for(int i = x; i < x + heightRoom; i++) {
                for (int j = y; j < y + widthRoom; j++) {
                    PatternBlockResource block = patterns.get(rolledPattern)[widthRoom  - (j-y) - 1][i-x];
                    //patterns.get(rolledPattern)[j - y][i - x];


                    if(block.getPattern().equals(PatternBlockResource.PATTERNS.SOLID_BLOCK)){
                        int roll = MathUtils.random(0, 100);
                        boolean isRolled = roll <= block.getChance();

                        map[i][j].setBool(isRolled);
                    }else if(block.getPattern().equals(PatternBlockResource.PATTERNS.EMPTY)){
                        map[i][j].setBool(BEMPTY);
                    }else if(block.getPattern().equals(PatternBlockResource.PATTERNS.LIGHT)){
                        map[i][j].setBool(BEMPTY);
                        ligths.add(new Coordinates(i,j));
                        //DecorationStruct struct = genDynamicDecoration(assets.findDecor("TORCH_00"),i,j);
                        //decorationStructs.add(struct);

                    }else if(block.getPattern().equals(PatternBlockResource.PATTERNS.IN_OUT)){
                        if(floor == 0){
                            inputs.add(new Coordinates(i,j));
                        }
                        map[i][j].setBool(BEMPTY);

                    }

                    map[i][j].setPattern(block.getPattern());

                }
            }
        }

    }

    void genBackground(boolean[][] ret) {

        background = new String[ret.length][ret[0].length];

        for(int i = 0; i < ret.length; i++){
            for(int j = 0; j < ret[0].length; j++){
                if(ret[i][j]){
                    TileResource tile = whatTile(ret,i ,j ,true);
                    if(tile != null) background[i][j] = tile.textureName[0][0];//todo: <-
                    //setChunkTile(tile,i,j);


                }else background[i][j] = null;
            }
        }
    }


    Array<Coordinates> setChunkTile(TileResource resource, int i, int j){
        if(resource == null){
            Gdx.app.error("Assets.setChunkTile","resource is null");
            return null;
        }

        Array<Coordinates> ret = new Array<Coordinates>();
        Array<String> recoil = new Array<String>();


        for(int tI = 0,mJ = j; tI < resource.textureName.length; tI++, mJ--) {
            for (int tJ = 0, mI = i; tJ < resource.textureName[0].length; tJ++, mI++) {
                if(!Utils.isFringle( mI,mJ,map.length,map[0].length)) {
                    recoil.add(map[mI][mJ].getTile());
                    map[mI][mJ].setTile(resource.textureName[tI][tJ], resource.canStep);
                    ret.add(new Coordinates(mI,mJ));
                }else {
                    //Gdx.app.log("Assets.fsetChunkTile ","fringle map");

                    if(ret.size == recoil.size) {

                        for (int r = 0; r < ret.size; r++) {
                            map[ ret.get(r).x ][ ret.get(r).y ].setTile( recoil.get(r) );
                            //Gdx.app.log("",recoil.get(r));
                        }
                    }else{
                        Gdx.app.error("Assets.fsetChunkTile ","ret.size == recoil.size");
                    }
                    ret.clear();
                    recoil.clear();

                    return null;
                }
            }
        }

        recoil.clear();

        return ret;
    }


    void genTiles(){

        boolean[][] bmap = getBooleanMap();

        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j].getBool()){
                    TileResource tile = whatTile(bmap,i ,j ,false);

                    setChunkTile(tile,i,j);

                    //if(tile != null) {
                        //map[i][j].setChunkTile(tile.textureName,tile.canStep);
                    //}

                }else map[i][j].setTile(null);
            }
        }
    }

    public boolean[][] getBooleanMap(){
        boolean[][] bmap = new boolean[map.length][map[0].length];

        for(int i = 0; i < bmap.length; i++){
            for(int j = 0; j < bmap[0].length; j++){
                bmap[i][j] = map[i][j].getBool();
            }
        }

        return bmap;
    }

    private TileResource whatTile(boolean[][] bmap,int i,int j, boolean isBg ){

        Array<Integer> fit = new Array<Integer>();

        TileResource[] tileResources = assets.getTiles();

        for (int tile = 0; tile < tileResources.length; tile++) {
            if(!tileResources[tile].isDecor && tileResources[tile].pattern.isApproach(bmap,i - 1 ,j + 1 ) ){

                fit.add(tile);
            }
        }


        if(fit.size == 0){
            Gdx.app.error("Map.whatTile","can't approach patterns");
            return null;
        }



        if(fit.size != 1){

            for(Iterator<Integer> it = fit.iterator();it.hasNext(); ){
                Integer index = it.next();
                if( (isBg && !tileResources[index].isBg) || (!isBg && tileResources[index].isBg )) {
                    it.remove();
                }
            }

            if(fit.size != 1) {
                Gdx.app.error("Map.WhatTile", "more 2 tiles " + fit.size);
            }

        }

        return fit.size == -1 ? null : tileResources[fit.first()] ;//fit.get(MathUtils.random(0,fit.size - 1));
    }


    static private int getXDir(int x, int dir){
        if(dir == 2 || dir == 4 || dir == 7) x++;
        if(dir == 0 || dir == 3 || dir == 5) x--;

        return x;
    }

    static private int getYDir(int y, int dir){
        if(dir == 0 || dir == 1 || dir == 2) y++;
        if(dir == 5 || dir == 6 || dir == 7) y--;
        return y;
    }

    static private boolean[] getInfo(boolean[][] map, int x, int y){
        boolean []ret = new boolean[8];

        for(int i = 0; i < ret.length; i++){
            ret[i] = getNeighbor(map,x,y,i);
        }

        return ret;
    }

    static private boolean getNeighbor(boolean[][] map,int x,int y,int dir){
        int     nX = getXDir(x,dir),
                nY = getYDir(y,dir);

        return Utils.isFringle(nX,nY,map.length,map[0].length) ? BBLOCKED : map[nX][nY];
    }



    private void findDecorationCells(boolean[][] map) {
        emptyCells.clear();
        decorFloor.clear();
        decorRoof.clear();

        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] == BEMPTY) {
                    emptyCells.add(new Coordinates(i,j));

                    if(map[i][j - 1] == BBLOCKED /*&& map[i-1][j-1] != BEMPTY && map[i+1][j-1] != BEMPTY*/ ) {
                        decorFloor.add(new Coordinates(i, j));
                    }else if(map[i][j + 1] == BBLOCKED){
                        decorRoof.add(new Coordinates(i,j));
                    }


                }
            }
        }
    }

    int[][] rooms(boolean [][]bmap){
        int[][] rooms = convertToRooms(bmap);
        int index = 1;
        for(int i = 0; i < rooms.length; i++){
            for(int j = 0; j < rooms[0].length; j++){
                if(rooms[i][j] == EMPTY){
                    markRoom(rooms,i,j,index++);
                }
            }
        }
        return rooms;
    }


    static public int[][] convertToRooms(boolean[][] map){
        return convertToRooms(map,false);
    }

    static public int[][] convertToRooms(boolean[][] map,boolean empty){
        int[][] ret = new int[map.length][map[0].length];

        for(int i = 0; i < ret.length; i++){
            for(int j = 0; j < ret[0].length; j++){
                if(empty)
                    ret[i][j] = EMPTY;
                else
                    ret[i][j] = map[i][j] ? BLOCKED : EMPTY;
            }
        }

        return ret;
    }

    void markRoom(int[][] rooms, int x,int y,int index){
        Array<Coordinates> xy = new Array<Coordinates>();
        xy.add(new Coordinates(x,y));
        Coordinates tmp = null;



        while(xy.size != 0){
            tmp = xy.removeIndex(0);
            if(rooms[tmp.x][tmp.y] == EMPTY || rooms[tmp.x][tmp.y] == CAP){
                rooms[tmp.x][tmp.y] = index;
            }

            Array<Coordinates> neighbors = getXYNeighbors(tmp.x,tmp.y,rooms.length,rooms[0].length,true);

            for(Coordinates n : neighbors){
                if(rooms[n.x][n.y] == EMPTY
                       /* && !Utils.isContains(xy,n)*/) {
                    rooms[n.x][n.y] = CAP;
                    xy.add(n);
                }
            }
        }

    }




    void makeTunnel(boolean[][] map, Array<Coordinates>  path){
        Gdx.app.log("WorldMap","Make tunnel");
        for(Coordinates it : path){
            map[it.x][it.y] = BEMPTY;
            Array<Coordinates> neighbors = getXYNeighbors(it.x,it.y,map.length,map[0].length,true);
            for(Coordinates itX : neighbors)
                map[itX.x][itX.y] = BEMPTY;

        }

    }



    void removeSpecificCells(boolean[][]map, Array<Coordinates> list,boolean cell){
        for(Iterator<Coordinates> it = list.iterator(); it.hasNext(); ){
            Coordinates c = it.next();
            if(map[c.x][c.y] == cell) it.remove();
        }
    }
    /*
          boolean [][]map - бинарная карта,
          Coordinates start - координаты старта
          Coordinates end - координаты конца
          boolean isEight - пуск волны в 8 сторон или в 4
          boolean isRule - двигаться по правилам( по Map.EMPTY );
       */
    Array<Coordinates> lee(Coordinates start, Coordinates end,boolean isEight,boolean isRule) {
        return lee(getBooleanMap(),start,end,isEight,isRule);
    }
    Array<Coordinates> lee(boolean[][] map,Coordinates start, Coordinates end,boolean isEight,boolean isRule){

        if(start.equals(end) ){
            Gdx.app.error("Map.lee","start == end");
            return new Array<Coordinates>();
        }

        //Gdx.app.log("PathFinder.leeEight","start: " + start.toString() + ", end: " + end.toString() + ",dist: " + Utils.distance(start.x,end.x,start.y,end.y));
        int[][] pathMap = WorldMap.convertToRooms(map,!isRule);

//        Gdx.app.log("PathFinder.leeEight","start: " + pathMap[start.x][start.y] + ", end: " + pathMap[end.x][end.y]);

        Array<Coordinates> heap = new Array<Coordinates>();
        heap.add(start);

        Coordinates tmp = null;


        while(heap.size != 0) {

            tmp = heap.removeIndex(0);

            if(pathMap[end.x][end.y] > WorldMap.EMPTY) break;


            Array<Coordinates> neighbors = removeBut(pathMap, getXYNeighbors(tmp.x,tmp.y,map.length,map[0].length,isEight), WorldMap.EMPTY);


            markDistance(pathMap,neighbors,start);
            heap.addAll(neighbors);

            //Gdx.app.log("PathFinder.leeEight","heap: " + heap.size());
        }

        /*System.out.print("lee map\n");
        for(int i = 0; i < pathMap.length; i++){
            for(int j = 0; j < pathMap[0].length; j++){
                System.out.print(pathMap[i][j] + " ");
            }
            System.out.print("\n");
        }*/

//        Gdx.app.log("PathFinder.leeEight","dist: " + pathMap[end.x][end.y]);

        return pathMap[end.x][end.y] == Utils.distance(start.x,end.x,start.y,end.y) ? reconstructPathLee(pathMap,start,end,isEight) : null;
    }

    static private void markDistance(int[][] map,Array<Coordinates> neighbors,Coordinates dist){
        for(Coordinates it : neighbors){
            map[it.x][it.y] = Utils.distance(dist.x,it.x,dist.y,it.y);
        }
    }

    static private Array<Coordinates> removeBut(int[][] map, Array<Coordinates> neighbors, int but){
        Array<Coordinates> ret = new Array<Coordinates>();
        for(Coordinates it : neighbors){
            if(it != null && map[it.x][it.y] == but) ret.add(it);
        }
        return ret;
    }

    Array<Coordinates> reconstructPathLee(int[][] pathMap, Coordinates start, Coordinates end,boolean isEight){
        Array<Coordinates> ret = new Array<Coordinates>();
        ret.add(end);
        Coordinates tmp = end;
        for(int dist = pathMap[end.x][end.y]; dist > 0; dist--){

            Array<Coordinates> neighbors = getXYNeighbors( tmp.x, tmp.y,pathMap.length,pathMap[0].length,isEight);

            tmp = findSpecDistance(neighbors,pathMap,start,dist - 1);
            if(tmp == null){
                tmp = findSpecDistance(neighbors,pathMap,start,dist);
                dist++;
                if(tmp == null) break;
            }

            pathMap[tmp.x][tmp.y] = 99;

            ret.add(tmp);
        }
        /*System.out.print("recon lee map\n");
        for(int i = 0; i < pathMap.length; i++){
            for(int j = 0; j < pathMap[0].length; j++){
                System.out.print(pathMap[i][j] + " ");
            }
            System.out.print("\n");
        }*/
        //return ret.get(ret.size() - 1).equals(start) ? ret : null;
        ret.reverse();
        return ret;
    }

    Coordinates findSpecDistance(Array<Coordinates> neighbors,int[][] pathMap,Coordinates start,int dist){
        if(neighbors == null) return null;
        Array<Coordinates> ret = new Array<Coordinates>();
        for(Coordinates it : neighbors){
            if(pathMap[it.x][it.y] == dist){
                ret.add(it);
            }
        }
        return findMinDist(ret,start);
    }

    Coordinates findMinDist(Array<Coordinates> set,Coordinates point){
        if(set.size == 0) return null;

        int iMin = 0;
        int min = distance(set.get(iMin),point);

        for(int i = 1; i < set.size; i++){
            if(distance(set.get(i),point) < min){
                iMin = i;
                min = distance(set.get(i),point);
            }
        }
        return set.get(iMin);
    }

    static private int distance(Coordinates A,Coordinates B){
        return Utils.distance(A.x,B.x,A.y,B.y);
    }

}

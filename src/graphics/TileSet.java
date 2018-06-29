package graphics;

import display.Game;

public class TileSet {

    public String name;
    private String coords;
    private Sprite[] tiles;
    private int defaultSprite = 0;
    private int lastError;

    private static String[] tileNames = {"tl","t","tr","ml","m","mr","bl","b","br","o","ot","ol","or","ob","e1","e2"};

    public TileSet(int[] sheet, String coords, String name){
        this.coords = coords;
        this.name = name;

        lastError = 0;
        parseCoords(sheet);


    }

    private void parseCoords(int[] sheet){
        String[] tileCoords = coords.split(";");
        tiles = new Sprite[tileCoords.length];
        int i = 0;
        for(String coord: tileCoords){
            if(coord.matches("X")){
                tiles[i++] = null;
                continue;
            }
            int x = Integer.parseInt(coord.split(",")[0]);
            int y = Integer.parseInt(coord.split(",")[1]);

            tiles[i++] = new Sprite(sheet, x, y, TileSprites.SPRITE_WIDTH, TileSprites.SPRITE_HEIGHT);
        }
    }

    public void setDefaultSprite(int index){
        if(index >= tiles.length){
            if(lastError != Game.getInstance().getUpdates() / Game.updatedPerSecond){
                System.out.println("Index is too large in tileset " + name + ".getDefaultSprite()!");
                lastError = Game.getInstance().getUpdates() / Game.updatedPerSecond;
            }
        } else {
            defaultSprite = index;
        }
    }

    public Sprite getTile(int tile){
        if(tile >= tiles.length){
            if(lastError != Game.getInstance().getUpdates() / Game.updatedPerSecond) {
                System.out.println("Tile is too large in tileset " + name + ".getTile()!");
                System.out.println("Defaulting to tile " + tileNames[defaultSprite]);
                lastError = Game.getInstance().getUpdates() / Game.updatedPerSecond;
            }
            return tiles[defaultSprite];
        }
        if(tiles[tile] == null){
            if(lastError != Game.getInstance().getUpdates() / Game.updatedPerSecond) {
                System.out.println("Tile " + tile + " is null in TileSet " + name + "!");
                System.out.println("Defaulting to tile " + tileNames[defaultSprite]);
                lastError = Game.getInstance().getUpdates() / Game.updatedPerSecond;
            }
        }
        return tiles[tile];
    }

    public Sprite getTile(String tile){
        int index = defaultSprite;
        int i = 0;
        for(String name: tileNames){
            if(name.matches(tile)){
                index = i;
                break;
            }
            i++;
        }
        if(index >= tiles.length || tiles[index] == null){
            index = defaultSprite;
            if(lastError != Game.getInstance().getUpdates() / Game.updatedPerSecond) {
                System.out.println("Tile " + tile + " is null in TileSet " + name + "!");
                System.out.println("Defaulting to tile " + tileNames[defaultSprite]);
                lastError = Game.getInstance().getUpdates() / Game.updatedPerSecond;
            }
        }
        if(index == defaultSprite && i >= tiles.length){
            if(lastError != Game.getInstance().getUpdates() / Game.updatedPerSecond) {
                System.out.println("Tile " + tile + " isn't a valid name!");
                System.out.println("Defaulting to tile " + tileNames[defaultSprite]);
                lastError = Game.getInstance().getUpdates() / Game.updatedPerSecond;
            }
        }
        return getTile(index);
    }
}

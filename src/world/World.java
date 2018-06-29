package world;

import display.Game;
import display.Screen;
import entity.Entity;
import entity.Player;
import graphics.TileSprites;
import input.JKeyboard;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

    private static final int GRASS = 4;
    private static final int WATER = 9;

    private static final int TL = 0;
    private static final int TR = 2;
    private static final int BL = 6;
    private static final int BR = 8;

    private static final int T = 1;
    private static final int L = 3;
    private static final int R = 5;
    private static final int B = 7;

    private static final int OT = 12;
    private static final int OL = 13;
    private static final int OR = 14;
    private static final int OB = 15;

    private static final int V = 10;
    private static final int H = 11;

    public static final int SIZE = 128;

    private int[][] tiles;
    private int[][] newTiles;

    private Player player;
    private List<Entity> entities;

    public World(){
        tiles = new int[SIZE][SIZE];

        player = new Player(16, 16);
        entities = new ArrayList<>();
        entities.add(new Entity(80, 80));

        generateTiles();
        smoothTiles();
    }

    private void generateTiles(){
        int[][] water = new int[SIZE][SIZE];

        Random rand = new Random();
        for(int i = 0; i < SIZE; i++){
            for(int k = 0; k < SIZE; k++){
                if(rand.nextInt() % 12 < 6){
                    water[i][k] = 1;
                } else {
                    water[i][k] = 0;
                }
                //water[i][k] = rand.nextInt() % 3; //0 is water 1 is grass
            }
        }

        for(int i = 0; i < SIZE; i++){
            for(int k = 0; k < SIZE; k++){
                if(water[i][k] == 0){
                    tiles[i][k] = WATER;
                } else {
                    tiles[i][k] = GRASS;
                }
            }
        }
    }

    private void smoothTiles(){
        long start = System.currentTimeMillis();
        newTiles = new int[SIZE][SIZE];

        // FIX OUT OF PLACE TILES

        for(int i = 0; i < SIZE; i++){
            for(int k = 0; k < SIZE; k++){

                // ===== CORNERS =====

                if(k == 0 && i == 0){                                                                         //TOP_LEFT
                    tiles[i][k] = GRASS;                                   //Forces tile to always be grass(spawn point)
                } else if(k == 0 && i == SIZE - 1 && tiles[i][k] == GRASS){                                  //TOP_RIGHT
                    if(tiles[i][k] == GRASS){
                        int val = 0;
                        if(tiles[i][k+1] == WATER) val++;
                        if(tiles[i-1][k] == WATER) val++;
                        if(val == 2){
                            tiles[i][k] = WATER;                            //Makes tile water if water is on both sides
                        }
                    }
                } else if(k == SIZE - 1 && i == 0 && tiles[i][k] == GRASS){                                //BOTTOM_LEFT
                    if(tiles[i][k] == GRASS){
                        int val = 0;
                        if(tiles[i][k-1] == WATER) val++;
                        if(tiles[i+1][k] == WATER) val++;
                        if(val == 2){
                            tiles[i][k] = WATER;                            //Makes tile water if water is on both sides
                        }
                    }
                } else if(k == SIZE - 1 && i == SIZE - 1 && tiles[i][k] == GRASS){                        //BOTTOM_RIGHT
                    if(tiles[i][k] == GRASS){
                        int val = 0;
                        if(tiles[i][k-1] == WATER) val++;
                        if(tiles[i-1][k] == WATER) val++;
                        if(val == 2){
                            tiles[i][k] = WATER;                            //Makes tile water if water is on both sides
                        }
                    }
                }

                // ===== END CORNERS =====

                // ===== EDGES =====



                // ===== END EDGES =====

                // ===== CENTER =====

                if(i != 0 && k != 0 && i != SIZE - 1 && k != SIZE - 1) {
                    int val = 0;
                    if (tiles[i][k - 1] == WATER) val++;
                    if (tiles[i][k + 1] == WATER) val++;
                    if (tiles[i - 1][k] == WATER) val++;
                    if (tiles[i + 1][k] == WATER) val++;
                    if (val == 4) {
                        tiles[i][k] = WATER;                                //Makes tile water if water is on both sides
                        System.out.println("Tile (" + i + ", " + k + ") changed to water cause of surroundings");
                    }
                }
            }
        }

        // CHANGE GRASS TO APPROPRIATE TILE IN TILESET

        for(int i = 0; i < SIZE; i++){
            for(int k = 0; k < SIZE; k++){

                newTiles[i][k] = GRASS;

                if(tiles[i][k] == WATER){
                    newTiles[i][k] = WATER;
                    continue;
                }

                // ===== CORNERS =====

                if(k == 0 && i == 0){                                                 //TOP_LEFT
                    if(tiles[i+1][k] == WATER && tiles[i][k+1] == WATER){
                        newTiles[i][k] = BR;
                    } else if(tiles[i+1][k] == WATER && tiles[i][k+1] == GRASS){
                        newTiles[i][k] = R;
                    } else if(tiles[i+1][k] == GRASS && tiles[i][k+1] == WATER){
                        newTiles[i][k] = B;
                    }
                } else if(k == 0 && i == SIZE - 1){                                  //TOP_RIGHT
                    if(tiles[i-1][k] == WATER && tiles[i][k+1] == WATER){
                        newTiles[i][k] = BL;
                    } else if(tiles[i-1][k] == WATER && tiles[i][k+1] == GRASS){
                        newTiles[i][k] = L;
                    } else if(tiles[i-1][k] == GRASS && tiles[i][k+1] == WATER){
                        newTiles[i][k] = B;
                    }
                } else if(k == SIZE - 1 && i == 0){                                //BOTTOM_LEFT
                    if(tiles[i+1][k] == WATER && tiles[i][k-1] == WATER){
                        newTiles[i][k] = TR;
                    } else if(tiles[i+1][k] == WATER && tiles[i][k-1] == GRASS){
                        newTiles[i][k] = R;
                    } else if(tiles[i+1][k] == GRASS && tiles[i][k-1] == WATER){
                        newTiles[i][k] = T;
                    }
                } else if(k == SIZE - 1 && i == SIZE - 1){                        //BOTTOM_RIGHT
                    if(tiles[i-1][k] == WATER && tiles[i][k-1] == WATER){
                        newTiles[i][k] = TL;
                    } else if(tiles[i-1][k] == WATER && tiles[i][k-1] == GRASS){
                        newTiles[i][k] = L;
                    } else if(tiles[i-1][k] == GRASS && tiles[i][k-1] == WATER){
                        newTiles[i][k] = T;
                    }
                }

                // ===== END CORNERS =====

                // ===== EDGES =====

                if(k == 0 && (i != 0 && i != SIZE - 1)){                             //TOP EDGES
                    if(tiles[i+1][k] == WATER && tiles[i][k+1] == WATER && tiles[i-1][k] == WATER){
                        newTiles[i][k] = OB;
                    } else if(tiles[i+1][k] == GRASS && tiles[i][k+1] == WATER && tiles[i-1][k] == WATER){
                        newTiles[i][k] = BL;
                    } else if(tiles[i+1][k] == WATER && tiles[i][k+1] == GRASS && tiles[i-1][k] == WATER){
                        newTiles[i][k] = V;
                    } else if(tiles[i+1][k] == WATER && tiles[i][k+1] == WATER && tiles[i-1][k] == GRASS){
                        newTiles[i][k] = BR;
                    } else if(tiles[i+1][k] == GRASS && tiles[i][k+1] == GRASS && tiles[i-1][k] == WATER){
                        newTiles[i][k] = L;
                    } else if(tiles[i+1][k] == GRASS && tiles[i][k+1] == WATER && tiles[i-1][k] == GRASS){
                        newTiles[i][k] = B;
                    } else if(tiles[i+1][k] == WATER && tiles[i][k+1] == GRASS && tiles[i-1][k] == GRASS){
                        newTiles[i][k] = R;
                    }
                } else if(k == SIZE - 1 && (i != 0 && i != SIZE - 1)){            //BOTTOM EDGES
                    if(tiles[i+1][k] == WATER && tiles[i][k-1] == WATER && tiles[i-1][k] == WATER){
                        newTiles[i][k] = OT;
                    } else if(tiles[i+1][k] == GRASS && tiles[i][k-1] == WATER && tiles[i-1][k] == WATER){
                        newTiles[i][k] = TL;
                    } else if(tiles[i+1][k] == WATER && tiles[i][k-1] == GRASS && tiles[i-1][k] == WATER){
                        newTiles[i][k] = V;
                    } else if(tiles[i+1][k] == WATER && tiles[i][k-1] == WATER && tiles[i-1][k] == GRASS){
                        newTiles[i][k] = TR;
                    } else if(tiles[i+1][k] == GRASS && tiles[i][k-1] == GRASS && tiles[i-1][k] == WATER){
                        newTiles[i][k] = L;
                    } else if(tiles[i+1][k] == GRASS && tiles[i][k-1] == WATER && tiles[i-1][k] == GRASS){
                        newTiles[i][k] = T;
                    } else if(tiles[i+1][k] == WATER && tiles[i][k-1] == GRASS && tiles[i-1][k] == GRASS){
                        newTiles[i][k] = R;
                    }
                } else if(i == 0 && (k != 0 && k != SIZE - 1)){                     //LEFT EDGES
                    if(tiles[i+1][k] == WATER && tiles[i][k-1] == WATER && tiles[i][k+1] == WATER){
                        newTiles[i][k] = OR;
                    } else if(tiles[i+1][k] == GRASS && tiles[i][k-1] == WATER && tiles[i][k+1] == WATER){
                        newTiles[i][k] = H;
                    } else if(tiles[i+1][k] == WATER && tiles[i][k-1] == GRASS && tiles[i][k+1] == WATER){
                        newTiles[i][k] = BR;
                    } else if(tiles[i+1][k] == WATER && tiles[i][k-1] == WATER && tiles[i][k+1] == GRASS){
                        newTiles[i][k] = TR;
                    } else if(tiles[i+1][k] == GRASS && tiles[i][k-1] == GRASS && tiles[i][k+1] == WATER){
                        newTiles[i][k] = B;
                    } else if(tiles[i+1][k] == GRASS && tiles[i][k-1] == WATER && tiles[i][k+1] == GRASS){
                        newTiles[i][k] = T;
                    } else if(tiles[i+1][k] == WATER && tiles[i][k-1] == GRASS && tiles[i][k+1] == GRASS){
                        newTiles[i][k] = R;
                    }
                } else if(i == SIZE - 1 && (k != 0 && k != SIZE - 1)){             //RIGHT EDGES
                    if(tiles[i-1][k] == WATER && tiles[i][k-1] == WATER && tiles[i][k+1] == WATER){
                        newTiles[i][k] = OL;
                    } else if(tiles[i-1][k] == GRASS && tiles[i][k-1] == WATER && tiles[i][k+1] == WATER){
                        newTiles[i][k] = H;
                    } else if(tiles[i-1][k] == WATER && tiles[i][k-1] == GRASS && tiles[i][k+1] == WATER){
                        newTiles[i][k] = BL;
                    } else if(tiles[i-1][k] == WATER && tiles[i][k-1] == WATER && tiles[i][k+1] == GRASS){
                        newTiles[i][k] = TL;
                    } else if(tiles[i-1][k] == GRASS && tiles[i][k-1] == GRASS && tiles[i][k+1] == WATER){
                        newTiles[i][k] = B;
                    } else if(tiles[i-1][k] == GRASS && tiles[i][k-1] == WATER && tiles[i][k+1] == GRASS){
                        newTiles[i][k] = T;
                    } else if(tiles[i-1][k] == WATER && tiles[i][k-1] == GRASS && tiles[i][k+1] == GRASS){
                        newTiles[i][k] = L;
                    }
                }

                // ===== END EDGES =====

                // ===== CENTER =====

                if(i != 0 && k != 0 && i != SIZE - 1 && k != SIZE - 1) {
                    if(tiles[i-1][k] == GRASS && tiles[i+1][k] == WATER && tiles[i][k+1] == WATER && tiles[i][k-1] == WATER){
                        newTiles[i][k] = OR;
                    } else if(tiles[i-1][k] == WATER && tiles[i+1][k] == GRASS && tiles[i][k+1] == WATER && tiles[i][k-1] == WATER){
                        newTiles[i][k] = OL;
                    } else if(tiles[i-1][k] == WATER && tiles[i+1][k] == WATER && tiles[i][k+1] == GRASS && tiles[i][k-1] == WATER){
                        newTiles[i][k] = OT;
                    } else if(tiles[i-1][k] == WATER && tiles[i+1][k] == WATER && tiles[i][k+1] == WATER && tiles[i][k-1] == GRASS){
                        newTiles[i][k] = OB;
                    } else if(tiles[i-1][k] == GRASS && tiles[i+1][k] == GRASS && tiles[i][k+1] == WATER && tiles[i][k-1] == WATER){
                        newTiles[i][k] = H;
                    } else if(tiles[i-1][k] == GRASS && tiles[i+1][k] == WATER && tiles[i][k+1] == GRASS && tiles[i][k-1] == WATER){
                        newTiles[i][k] = TR;
                    } else if(tiles[i-1][k] == GRASS && tiles[i+1][k] == WATER && tiles[i][k+1] == WATER && tiles[i][k-1] == GRASS){
                        newTiles[i][k] = BR;
                    } else if(tiles[i-1][k] == WATER && tiles[i+1][k] == GRASS && tiles[i][k+1] == GRASS && tiles[i][k-1] == WATER){
                        newTiles[i][k] = TL;
                    } else if(tiles[i-1][k] == WATER && tiles[i+1][k] == GRASS && tiles[i][k+1] == WATER && tiles[i][k-1] == GRASS){
                        newTiles[i][k] = BL;
                    } else if(tiles[i-1][k] == WATER && tiles[i+1][k] == WATER && tiles[i][k+1] == GRASS && tiles[i][k-1] == GRASS){
                        newTiles[i][k] = V;
                    } else if(tiles[i-1][k] == GRASS && tiles[i+1][k] == GRASS && tiles[i][k+1] == GRASS && tiles[i][k-1] == WATER){
                        newTiles[i][k] = T;
                    } else if(tiles[i-1][k] == GRASS && tiles[i+1][k] == GRASS && tiles[i][k+1] == WATER && tiles[i][k-1] == GRASS){
                        newTiles[i][k] = B;
                    } else if(tiles[i-1][k] == GRASS && tiles[i+1][k] == WATER && tiles[i][k+1] == GRASS && tiles[i][k-1] == GRASS){
                        newTiles[i][k] = R;
                    } else if(tiles[i-1][k] == WATER && tiles[i+1][k] == GRASS && tiles[i][k+1] == GRASS && tiles[i][k-1] == GRASS){
                        newTiles[i][k] = L;
                    }
                }
            }
        }
        //tiles = newTiles;
        System.out.println("Smoothing took " + (System.currentTimeMillis() - start) + " milliseconds");
    }

    public void render(Screen screen, JKeyboard input){
        for(int i = 0; i < SIZE; i++){
            for(int k = 0; k < SIZE; k++){
                if(input.isKeyPressed(KeyEvent.VK_SHIFT)){
                    screen.render(TileSprites.getGrassWater().getTile(tiles[i][k]), i * 16, k * 16);
                } else {
                    screen.render(TileSprites.getGrassWater().getTile(newTiles[i][k]), i * 16, k * 16);
                }
            }
        }

        for(Entity e: entities){
            e.render(screen);
        }

        player.render(screen);
    }

    public void update(JKeyboard input){
        player.update(input);

        if(player.getMoved()){
            for(Entity e: entities){
                e.makeMove();
                if(e.getxPos() == player.getxPos() && e.getyPos() == player.getyPos()){
                    Game.intersected =  true;
                }
            }
        }
    }

    public boolean isTileType(int x, int y, int type){
        if(x >= SIZE || x < 0 || y >= SIZE || y < 0) return true;
        if(tiles[x][y] == type) return true;
        else return false;
    }
}

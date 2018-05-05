package model.entity.map.tiles;

import model.entity.tower.Tower;

public class WallTile extends MapTile {

	Tower myTower;
	
	public WallTile() {
		super(TileType.WALL);
	}
	
}

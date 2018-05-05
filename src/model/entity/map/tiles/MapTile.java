package model.entity.map.tiles;

import java.io.Serializable;

public abstract class MapTile implements Serializable {
	
	private TileType type;
	
	public MapTile(TileType tileType) {
		this.type = tileType;
	}
	
	public TileType getTileType() {
		return this.type;
	}
	
}

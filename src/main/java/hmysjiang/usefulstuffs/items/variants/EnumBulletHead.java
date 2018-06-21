package hmysjiang.usefulstuffs.items.variants;

import net.minecraft.util.IStringSerializable;

public enum EnumBulletHead implements IStringSerializable {
	UNBROKEN(0, "unbroken"),
	BROKEN(1, "broken");
	
	private int ID;
	private String name;
	
	private EnumBulletHead(int id, String name) {
		this.ID = id;
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	public int getID() {
		return this.ID;
	}
	
}

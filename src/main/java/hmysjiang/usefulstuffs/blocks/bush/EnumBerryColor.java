package hmysjiang.usefulstuffs.blocks.bush;

import net.minecraft.util.IStringSerializable;

public enum EnumBerryColor implements IStringSerializable {
	WHITE(0, "white"),
    ORANGE(1, "orange"),
    MAGENTA(2, "magenta"),
    LIGHT_BLUE(3, "light_blue"),
    YELLOW(4, "yellow"),
    LIME(5, "lime"),
    PINK(6, "pink"),
    GRAY(7, "gray"),
    SILVER(8, "silver"),
    CYAN(9, "cyan"),
    PURPLE(10, "purple"),
    BLUE(11, "blue"),
    BROWN(12, "brown"),
    GREEN(13, "green"),
    RED(14, "red"),
    BLACK(15, "black");
	
	private int meta;
	private String name;
	
	private static final EnumBerryColor[] META_LOOKUP = new EnumBerryColor[values().length];
	
	private EnumBerryColor(int meta, String name) {
		this.meta = meta;
		this.name = name;
	}

	@Override
	public String getName() {
		return this.getName();
	}

    public int getMetadata() {
        return this.meta;
    }
    
    public String getDyeColorName() {
        return this.name;
    }
    
    public static EnumBerryColor byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length)
        {
            meta = 0;
        }

        return META_LOOKUP[meta];
    }
    
    static
    {
        for (EnumBerryColor enumdyecolor : values())
        {
            META_LOOKUP[enumdyecolor.getMetadata()] = enumdyecolor;
        }
    }
	
}

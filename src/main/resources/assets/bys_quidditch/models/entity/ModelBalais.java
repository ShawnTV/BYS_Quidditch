// Made with Blockbench 3.8.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


public class custom_model extends ModelBase {
	private final ModelRenderer bb_main;

	public custom_model() {
		textureWidth = 16;
		textureHeight = 16;

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -16.0F, -2.0F, -1.0F, 24, 2, 2, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, 8.0F, -3.0F, -2.0F, 1, 4, 4, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, 17.0F, -4.0F, -3.0F, -8, 6, 6, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bb_main.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
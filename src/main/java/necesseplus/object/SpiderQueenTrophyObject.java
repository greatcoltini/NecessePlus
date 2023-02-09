package necesseplus.object;

import necesse.engine.tickManager.TickManager;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.objectEntity.ObjectEntity;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.Item.Rarity;
import necesse.inventory.item.toolItem.ToolType;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import necesse.level.gameObject.furniture.FurnitureObject;

import java.awt.*;
import java.util.List;

public class SpiderQueenTrophyObject extends FurnitureObject {

    private GameTexture texture;

    public SpiderQueenTrophyObject() {
        super(new Rectangle(0, 0, 32, 32)); // Collision relative to the tile it's placed on
        this.rarity = Rarity.LEGENDARY;
        // Remember that tiles are 32x32 pixels in size
        hoverHitbox = new Rectangle(0, -32, 32, 64); // 2 tiles high mouse hover hitbox
        toolType = ToolType.ALL; // Can be broken by all tools
        isLightTransparent = false; // Lets light pass through
        mapColor = new Color(31, 150, 148); // Also applies as debris color if not set
    }

    @Override
    public void loadTextures() {
        super.loadTextures();
        texture = GameTexture.fromFile("objects/spiderqueentrophyobject");
    }

    @Override
    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        // Use the rotation if you have rotation on your object
//        int rotation = level.getObjectRotation(tileX, tileY);
        TextureDrawOptions options = texture.initDraw().light(light).pos(drawX, drawY - texture.getHeight() + 32);
        // Can choose sprite with texture.initDraw().sprite(...)

        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            @Override
            public int getSortY() {
                // Basically where this will be sorted on the Y axis (when it will be behind the player etc.)
                // Should be in [0 - 32] range
                return 16;
            }

            @Override
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }

    @Override
    public void drawPreview(Level level, int tileX, int tileY, int rotation, float alpha, PlayerMob player, GameCamera camera) {
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        texture.initDraw().alpha(alpha).draw(drawX, drawY - texture.getHeight() + 32);
    }

    @Override
    public ObjectEntity getNewObjectEntity(Level level, int x, int y) {
        // If this object has an object entity, return something else
        return null;
    }
}

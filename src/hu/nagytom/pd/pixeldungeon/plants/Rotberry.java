package hu.nagytom.pd.pixeldungeon.plants;

import hu.nagytom.pd.noosa.audio.Sample;
import hu.nagytom.pd.pixeldungeon.Assets;
import hu.nagytom.pd.pixeldungeon.Dungeon;
import hu.nagytom.pd.pixeldungeon.actors.Char;
import hu.nagytom.pd.pixeldungeon.actors.blobs.Blob;
import hu.nagytom.pd.pixeldungeon.actors.blobs.ToxicGas;
import hu.nagytom.pd.pixeldungeon.actors.buffs.Buff;
import hu.nagytom.pd.pixeldungeon.actors.buffs.Roots;
import hu.nagytom.pd.pixeldungeon.actors.mobs.Mob;
import hu.nagytom.pd.pixeldungeon.effects.CellEmitter;
import hu.nagytom.pd.pixeldungeon.effects.Speck;
import hu.nagytom.pd.pixeldungeon.items.bags.Bag;
import hu.nagytom.pd.pixeldungeon.items.potions.PotionOfStrength;
import hu.nagytom.pd.pixeldungeon.scenes.GameScene;
import hu.nagytom.pd.pixeldungeon.sprites.ItemSpriteSheet;
import hu.nagytom.pd.pixeldungeon.utils.GLog;

public class Rotberry extends Plant {

	private static final String TXT_DESC =
		"Berries of this shrub taste like sweet, sweet death.";

	{
		image = 7;
		plantName = "Rotberry";
	}

	@Override
	public void activate( Char ch ) {
		super.activate( ch );

		GameScene.add( Blob.seed( pos, 100, ToxicGas.class ) );

		Dungeon.level.drop( new Seed(), pos ).sprite.drop();

		if (ch != null) {
			Buff.prolong( ch, Roots.class, Roots.TICK * 3 );
		}
	}

	@Override
	public String desc() {
		return TXT_DESC;
	}

	public static class Seed extends Plant.Seed {
		{
			plantName = "Rotberry";

			name = "seed of " + plantName;
			image = ItemSpriteSheet.SEED_ROTBERRY;

			plantClass = Rotberry.class;
			alchemyClass = PotionOfStrength.class;
		}

		@Override
		public boolean collect( Bag container ) {
			if (super.collect( container )) {

				if (Dungeon.level != null) {
					for (Mob mob : Dungeon.level.mobs) {
						mob.beckon( Dungeon.hero.pos );
					}

					GLog.w( "The seed emits a roar that echoes throughout the dungeon!" );
					CellEmitter.center( Dungeon.hero.pos ).start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
					Sample.INSTANCE.play( Assets.SND_CHALLENGE );
				}

				return true;
			} else {
				return false;
			}
		}

		@Override
		public String desc() {
			return TXT_DESC;
		}
	}
}

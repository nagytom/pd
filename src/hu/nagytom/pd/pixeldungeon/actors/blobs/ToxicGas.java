/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package hu.nagytom.pd.pixeldungeon.actors.blobs;

import hu.nagytom.pd.pixeldungeon.Badges;
import hu.nagytom.pd.pixeldungeon.Dungeon;
import hu.nagytom.pd.pixeldungeon.ResultDescriptions;
import hu.nagytom.pd.pixeldungeon.actors.Actor;
import hu.nagytom.pd.pixeldungeon.actors.Char;
import hu.nagytom.pd.pixeldungeon.actors.hero.Hero;
import hu.nagytom.pd.pixeldungeon.effects.BlobEmitter;
import hu.nagytom.pd.pixeldungeon.effects.Speck;
import hu.nagytom.pd.pixeldungeon.utils.GLog;
import hu.nagytom.pd.pixeldungeon.utils.Utils;
import hu.nagytom.pd.utils.Random;

public class ToxicGas extends Blob implements Hero.Doom {

    @Override
    protected void evolve() {
        super.evolve();

        int levelDamage = 5 + Dungeon.depth * 5;

        Char ch;
        for (int i=0; i < LENGTH; i++) {
            if (cur[i] > 0 && (ch = Actor.findChar( i )) != null) {

                int damage = (ch.HT + levelDamage) / 40;
                if (Random.Int( 40 ) < (ch.HT + levelDamage) % 40) {
                    damage++;
                }

                ch.damage( damage, this );
            }
        }

        Blob blob = Dungeon.level.blobs.get( ParalyticGas.class );
        if (blob != null) {

            int par[] = blob.cur;

            for (int i=0; i < LENGTH; i++) {

                int t = cur[i];
                int p = par[i];

                if (p >= t) {
                    volume -= t;
                    cur[i] = 0;
                } else {
                    blob.volume -= p;
                    par[i] = 0;
                }
            }
        }
    }

    @Override
    public void use( BlobEmitter emitter ) {
        super.use( emitter );

        emitter.pour( Speck.factory( Speck.TOXIC ), 0.6f );
    }

    @Override
    public String tileDesc() {
        return "A greenish cloud of toxic gas is swirling here.";
    }

    @Override
    public void onDeath() {

        Badges.validateDeathFromGas();

        Dungeon.fail( Utils.format( ResultDescriptions.GAS, Dungeon.depth ) );
        GLog.n( "You died from a toxic gas.." );
    }
}

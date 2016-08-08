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
package hu.nagytom.pd.pixeldungeon.sprites;

import hu.nagytom.pd.noosa.particles.Emitter;
import hu.nagytom.pd.pixeldungeon.actors.Char;
import hu.nagytom.pd.pixeldungeon.effects.Speck;

public class FetidRatSprite extends RatSprite {

    private Emitter cloud;

    @Override
    public void link( Char ch ) {
        super.link( ch );

        if (cloud == null) {
            cloud = emitter();
            cloud.pour( Speck.factory( Speck.PARALYSIS ), 0.7f );
        }
    }

    @Override
    public void update() {

        super.update();

        if (cloud != null) {
            cloud.visible = visible;
        }
    }

    @Override
    public void die() {
        super.die();

        if (cloud != null) {
            cloud.on = false;
        }
    }
}

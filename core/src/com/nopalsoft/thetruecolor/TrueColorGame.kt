package com.nopalsoft.thetruecolor

import com.badlogic.gdx.utils.Array as GdxArray
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.nopalsoft.thetruecolor.handlers.FacebookHandler
import com.nopalsoft.thetruecolor.handlers.GameServicesHandler
import com.nopalsoft.thetruecolor.handlers.HandlerGWT
import com.nopalsoft.thetruecolor.handlers.HandlerGWT.OnTextureLoaded
import com.nopalsoft.thetruecolor.handlers.RequestHandler
import com.nopalsoft.thetruecolor.leaderboard.Person
import com.nopalsoft.thetruecolor.leaderboard.Person.AccountType
import com.nopalsoft.thetruecolor.screens.MainMenuScreen
import com.nopalsoft.thetruecolor.screens.Screens

class TrueColorGame(
    @JvmField
    val reqHandler: RequestHandler,
    @JvmField
    val gameServiceHandler: GameServicesHandler,
    @JvmField
    val facebookHandler: FacebookHandler,
    private val handlerGWT: HandlerGWT
) : Game() {

    @JvmField
    var arrPerson: GdxArray<Person>? = null
    lateinit var stage: Stage
    lateinit var batcher: SpriteBatch

    override fun create() {
        stage =
            Stage(StretchViewport(Screens.SCREEN_WIDTH.toFloat(), Screens.SCREEN_HEIGHT.toFloat()))
        batcher = SpriteBatch()

        Settings.load()
        Assets.load()
        Achievements.initialize(this)
        screen = MainMenuScreen(this)
    }

    fun setArrayPerson(_arrPerson: GdxArray<Person>) {
        if (arrPerson == null) {
            arrPerson = _arrPerson
        } else {
            for (oPerson in _arrPerson) {
                if (!arrPerson!!.contains(oPerson, false)) { // false to compare by equals
                    arrPerson!!.add(oPerson)
                } else {
                    val index = arrPerson!!.indexOf(oPerson, false)
                    arrPerson!![index].updateData(oPerson.name, oPerson.score)
                }
            }
        }

        arrPerson?.forEach { getPersonPhoto(it) }

        // If I'm not in the main menu, it doesn't update.
        if (screen is MainMenuScreen) {
            (screen as MainMenuScreen).updateLeaderboard()
        }
    }

    private fun getPersonPhoto(oPerson: Person) {
        handlerGWT.getTextureFromFacebook("https://picsum.photos/200", object : OnTextureLoaded {
            override fun onTextureLoaded(texture: Texture?) {
                oPerson.setPicture(TextureRegionDrawable(TextureRegion(texture)))
            }
        })
    }

    /**
     * Called when the Facebook session is closed, removes all Facebook users from the table.
     */
    fun removeFromArray(cuenta: AccountType) {
        if (arrPerson == null) return

        val i = arrPerson!!.iterator()
        while (i.hasNext()) {
            val obj = i.next()
            if (obj.accountType == cuenta) i.remove()
        }

        // If I'm not in the main menu, it won't update.
        if (getScreen() is MainMenuScreen) {
            val oScreen = getScreen() as MainMenuScreen
            oScreen.updateLeaderboard()
        }
    }
}
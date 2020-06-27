package za.co.lebogangnkosi.apptranslatelib.model

import com.google.gson.annotations.SerializedName

class Data {
    @SerializedName("translations")
    private var translations: ArrayList<Translation>? = null

    fun getTranslations(): ArrayList<Translation>? {
        return translations
    }

    fun setTranslations(translations: ArrayList<Translation>) {
        this.translations = translations
    }
}
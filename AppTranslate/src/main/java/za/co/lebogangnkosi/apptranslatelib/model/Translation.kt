package za.co.lebogangnkosi.apptranslatelib.model

import com.google.gson.annotations.SerializedName

class Translation {

    @SerializedName("translatedText")
    var mTranslatedText: String = ""

    fun getTranslatedText(): String {
        return mTranslatedText
    }

    fun setTranslatedString(mString: String) {
        mTranslatedText = mString
    }

}
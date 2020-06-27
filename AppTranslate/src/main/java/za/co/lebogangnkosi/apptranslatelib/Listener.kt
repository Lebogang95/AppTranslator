package za.co.lebogangnkosi.apptranslatelib

import za.co.lebogangnkosi.apptranslatelib.exceptions.TranslateException

interface Listener {

    fun onSuccess(linkedHashMap: LinkedHashMap<Int, String>)

    fun onError(e: TranslateException)

}
package za.co.lebogangnkosi.apptranslatelib.model

import com.google.gson.annotations.SerializedName

class FrontFacing {

    @SerializedName("data")
    private var data: Data? = null

    fun getData(): Data? {
        return data
    }

    fun setData(data: Data?) {
        this.data = data
    }

}
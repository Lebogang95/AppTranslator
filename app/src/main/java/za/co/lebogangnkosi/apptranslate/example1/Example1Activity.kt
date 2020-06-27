package za.co.lebogangnkosi.apptranslate.example1

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.facebook.stetho.Stetho
import za.co.lebogangnkosi.apptranslate.R
import za.co.lebogangnkosi.apptranslate.databinding.ActivityMainBinding
import za.co.lebogangnkosi.apptranslatelib.Translate
import za.co.lebogangnkosi.apptranslatelib.common.Listener
import za.co.lebogangnkosi.apptranslatelib.exceptions.TranslateException


/**
 * Example Activity to translate the content of it's Layout manually
 */
class Example1Activity : AppCompatActivity() {

    /**
     * Int: This is the ID of the String you want to translate. Use this as such : setText(hashmap.get(ID))
     * String: The string you want to translate
     */
    private var arrayList: ArrayList<String> = ArrayList()

    /**
     * ApiKey: ApiKey from Google Cloud
     * SourceLanguage: The language your text is currently in
     * TargetLanguage: The language you want to translate your text to
     *
     * If you use this SDK in production apps and want to kill the SDK in case of compatability issues you can use this flag. We recommend you get this from a backend service
     * If you don't have APIs for your app we recommend you use Firebase to get started
     */
    var mTranslate = Translate(
        "AIzaSyAvgzOSHbtr0ShUHlGGBbiM27DL6tyfHlc",
        "en",
        "zu",
        true
    )

    /**
     * DataBinding for this Activity
     */
    lateinit var mBinding: ActivityMainBinding

    var alinkedHashMap: LinkedHashMap<Int, String> = LinkedHashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Stetho.initializeWithDefaults(this);
        mBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        arrayList.add("Toolbar") //The order in which you put them is the order in which you obtain them. So this is second on the list, to get it use linkedHashMap[0]
        arrayList.add("Navigation Drawer") //1
        arrayList.add("BottomSheet") //2
        mBinding.button.setOnClickListener {
            mTranslate.createService(arrayList, object : Listener {
                override fun onSuccess(linkedHashMap: LinkedHashMap<Int, String>) {
                    alinkedHashMap = linkedHashMap
                    configure()
                }

                override fun onError(e: TranslateException) {
                    Toast.makeText(this@Example1Activity, e.errorMessage, Toast.LENGTH_LONG).show()
                }
            })
        }


    }

    private fun configure() {
        mBinding.text.text =
            alinkedHashMap[0] //2 Notice we get the Strings in the same order we put them
    }

}

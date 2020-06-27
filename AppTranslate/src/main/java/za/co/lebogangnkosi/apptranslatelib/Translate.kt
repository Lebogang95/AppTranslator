package za.co.lebogangnkosi.apptranslatelib

import androidx.annotation.NonNull
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import za.co.lebogangnkosi.apptranslatelib.common.Listener
import za.co.lebogangnkosi.apptranslatelib.exceptions.TranslateException
import za.co.lebogangnkosi.apptranslatelib.model.FrontFacing
import za.co.lebogangnkosi.apptranslatelib.model.Translation
import za.co.lebogangnkosi.apptranslatelib.network.BaseApiService
import za.co.lebogangnkosi.apptranslatelib.util.UtilsApi

class Translate(
    @NonNull aApiKey: String,
    @NonNull sourceLanguage: String,
    @NonNull targetLanguage: String,
    @NonNull shouldUseSdk: Boolean
) {

    var mApiService: BaseApiService? = null

    var mApiKey: String = aApiKey

    var mSourceLanguage: String = sourceLanguage

    var mTargetLanguage: String = targetLanguage

    var mShouldUseSdk: Boolean = shouldUseSdk

    var mLinkedHashMap: LinkedHashMap<Int, String> = LinkedHashMap()

    var mArrayList: ArrayList<String> = ArrayList()

    lateinit var mListener: Listener

    var disposable: Disposable? = null

    fun createService(aArrayList: ArrayList<String>, aListener: Listener) {
        if (mShouldUseSdk) {
            mListener = aListener
            mArrayList = aArrayList
            sortLinkedHashMap(aArrayList)
            mApiService = UtilsApi.getAPIService()
            validateParams()
            requestRepos()
        } else {
            sortLinkedHashMap(aArrayList)
            mListener.onSuccess(mLinkedHashMap)
        }
    }

    private fun sortLinkedHashMap(arrayList: ArrayList<String>) {
        arrayList.forEachIndexed { index, value ->
            mLinkedHashMap[index] = value
        }
    }

    private fun validateParams() {

    }

    private fun requestRepos() {
        mApiService!!.translate(mApiKey, mSourceLanguage, mTargetLanguage, mArrayList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<FrontFacing> {
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(responseRepos: FrontFacing) {
                    responseRepos.getData()?.getTranslations()?.forEach { aTranslation ->
                        aTranslation.getTranslatedText()
                    }
                    doSomething(responseRepos.getData()?.getTranslations())
                }

                override fun onError(e: Throwable) {
                    e.message
                }

                override fun onComplete() {
                    disposable?.dispose()
                }
            })
    }

    private fun doSomething(aArrayList: ArrayList<Translation>?) {
        if (aArrayList != null) {
            aArrayList.forEachIndexed { index, value ->
                mLinkedHashMap[index] = value.getTranslatedText()
            }
            mListener.onSuccess(mLinkedHashMap)
        } else {
            val translationException = TranslateException()
            translationException.errorMessage = "Something Happened"
            mListener.onError(translationException)
        }
    }
}
package za.co.lebogangnkosi.apptranslatelib

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import za.co.lebogangnkosi.apptranslatelib.exceptions.TranslateException
import za.co.lebogangnkosi.apptranslatelib.model.FrontFacing
import za.co.lebogangnkosi.apptranslatelib.model.Translation
import za.co.lebogangnkosi.apptranslatelib.network.BaseApiService
import za.co.lebogangnkosi.apptranslatelib.util.UtilsApi

class Translate(aApiKey: String, sourceLanguage: String, targetLanguage: String, shouldUseSdk: Boolean) {

    private var mApiService: BaseApiService? = null

    private var mApiKey: String = aApiKey

    private var mSourceLanguage: String = sourceLanguage

    private var mTargetLanguage: String = targetLanguage

    private var mShouldUseSdk: Boolean = shouldUseSdk

    private var mLinkedHashMap: LinkedHashMap<Int, String> = LinkedHashMap()

    private var mArrayList: ArrayList<String> = ArrayList()

    private lateinit var mListener: Listener

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
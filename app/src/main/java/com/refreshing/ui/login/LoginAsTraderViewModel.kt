package com.refreshing.ui.login

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson

import com.refreshing.datalayer.apis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginAsTraderViewModel @Inject constructor(private val apis: apis,
                                                 private val sharedPreferences: SharedPreferences
) :ViewModel(){

    fun logOut(){
        sharedPreferences.edit().putString("login","").commit()
    }


    val islogined: Boolean = !sharedPreferences.getString("login","").toString().equals("")
    val sucess :MutableLiveData<String> = MutableLiveData()
    fun loginTrader(sendLogin: HashMap<String,String>){
        viewModelScope.launch {
            kotlin.runCatching {

                apis.login(sendLogin)
            }.onFailure {
                sucess.postValue("fail")

            }.onSuccess {
                sharedPreferences.edit().putString("login","login").commit()

                sucess.postValue("done")
            }
        }


    }


}
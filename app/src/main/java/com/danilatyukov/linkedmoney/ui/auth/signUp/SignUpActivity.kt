package com.danilatyukov.linkedmoney.ui.auth.signUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.danilatyukov.linkedmoney.BaseActivity
import com.danilatyukov.linkedmoney.MainActivity
import com.danilatyukov.linkedmoney.R
import com.danilatyukov.linkedmoney.data.local.preferences.AppPreferences
import com.danilatyukov.linkedmoney.databinding.ActivityAuthBinding
import com.danilatyukov.linkedmoney.databinding.ActivitySignUpBinding
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity(), SignUpView, View.OnClickListener {

    private lateinit var _binding: ActivitySignUpBinding
    private val binding get() = _binding
    lateinit var presenter: SignUpPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        presenter = SignUpPresenterImpl(this)
        presenter.preferences = AppPreferences.create(this)
        bindViews()
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btnSignUp2) {
            presenter.executeSignUp(etUsername2.text.toString(), etPassword2.text.toString())
        }
    }

    fun bindViews() {
        btnSignUp2.setOnClickListener(this)
    }

    override fun setUsernameError() {
        etUsername2.error = "некорректный email"
    }

    override fun setPasswordError(msg: String) {
        etPassword2.error = msg
    }

    override fun showSignUpError() {
        showToast("Ошибка регистрации")
    }


    override fun showProgress() {
        progressBar2.visibility = View.VISIBLE
    }

    override fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun hideProgress() {
        progressBar2.visibility = View.GONE
    }

    override fun showAuthError() {
        showToast("Ошибка авторизации")
    }
}
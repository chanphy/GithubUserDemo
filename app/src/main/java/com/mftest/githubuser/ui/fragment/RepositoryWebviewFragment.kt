package com.mftest.githubuser.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mftest.githubuser.databinding.FragmentRepositoryWebviewBinding

class RepositoryWebviewFragment : Fragment() {
    private lateinit var binding: FragmentRepositoryWebviewBinding
    private val args: RepositoryWebviewFragmentArgs by navArgs() // Safe Args Auto-generate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoryWebviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("RepositoryListFragment", "Get url: ${args.url}")
        val url = args.url
        setupWebView(url)

    }

    private fun setupWebView(url: String) {
        with(binding.webView) {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                cacheMode = WebSettings.LOAD_DEFAULT
            }
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    }

}
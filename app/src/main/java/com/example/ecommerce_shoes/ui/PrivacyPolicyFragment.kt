package com.example.ecommerce_shoes.ui

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannedString
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.getSpans
import com.example.ecommerce_shoes.MainActivity
import com.example.ecommerce_shoes.R
import com.example.ecommerce_shoes.databinding.FragmentContactBinding
import com.example.ecommerce_shoes.databinding.FragmentPrivacyPolicyBinding
import com.example.ecommerce_shoes.util.CustomTypefaceSpan

class PrivacyPolicyFragment : Fragment() {

    private lateinit var binding: FragmentPrivacyPolicyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrivacyPolicyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        privacyPolicyLoad()
    }

    private fun privacyPolicyLoad() {
        var text = getText(R.string.privacy_policy_content) as SpannedString
        val spannedText = SpannableString(text)

        val annotations = text.getSpans(0, text.length, Annotation::class.java)

        for (annotation in annotations) {
            val textStartPos = text.getSpanStart(annotation)
            val textEndPos = text.getSpanEnd(annotation)
            val spanFlag = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE

            val keyValue = text.substring(textStartPos, textEndPos)
            val keyValueArray = keyValue.split("=")

            if (keyValueArray.size == 2) {
                val key = keyValueArray[0]
                val value = keyValueArray[1]

                when {
                    key == "title" -> formatTitleSpan(
                        spannedText,
                        textStartPos,
                        textEndPos,
                        value
                    )
                    key == "link" -> formatLinkSpan(
                        spannedText,
                        textStartPos,
                        textEndPos,
                        value
                    )
                }
            }
        }
        binding.tvPrivacyPolicyContent.text = spannedText.trimStart()
    }

    private fun formatTitleSpan(
        spannable: Spannable,
        start: Int,
        end: Int,
        value: String
    ) {
        val typeFace = ResourcesCompat.getFont(requireActivity(), R.font.pathway_gothic_one)
        val sizeSpan = when (value) {
            "main" -> RelativeSizeSpan(1.5F)
            "sub" -> RelativeSizeSpan(1.3F)
            else -> RelativeSizeSpan(1.1F)
        }

        spannable.apply {
            setSpan(CustomTypefaceSpan(typeFace!!), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(sizeSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun formatLinkSpan(
        spannable: Spannable,
        start: Int,
        end: Int,
        value: String
    ) {
        spannable.apply {
            setSpan(URLSpan(value), start + 1, end - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(requireActivity(), R.color.colorLink)
                ),
                start + 1,
                end - 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
    override fun onResume() {
        super.onResume()
        (activity as MainActivity).updateToolbarTitleInFragment(R.string.privacy_policy_frag_title)
    }
}

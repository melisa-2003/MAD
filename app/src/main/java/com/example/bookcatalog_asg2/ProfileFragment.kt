package com.example.bookcatalog_asg2

import android.content.Intent // 修正 #1：导入 Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bookcatalog_asg2.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)
        auth = FirebaseAuth.getInstance()

        loadUserData()
        setupClickListeners()
    }

    private fun loadUserData() {
        val currentUser = auth.currentUser
        // 如果用户为空，MainActivity的onStart()会处理跳转，这里无需额外操作
        if (currentUser != null) {
            binding.usernameTextView.text = currentUser.displayName ?: "Username"
            binding.emailTextView.text = currentUser.email ?: "Email Address"
        }
    }

    private fun setupClickListeners() {
        binding.topAppBar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // 更新ID
        binding.interestsContainer.setOnClickListener {
            navigateToFragment(InterestsFragment())
        }

        // 更新ID
        binding.manageNotificationContainer.setOnClickListener {
            navigateToFragment(ManageNotificationFragment())
        }

        // 更新ID
        binding.themeContainer.setOnClickListener {
            Toast.makeText(context, "Theme clicked", Toast.LENGTH_SHORT).show()
        }

        // 更新ID
        binding.aboutUsContainer.setOnClickListener {
            Toast.makeText(context, "About Us clicked", Toast.LENGTH_SHORT).show()
        }

        // 更新ID
        binding.contactUsContainer.setOnClickListener {
            Toast.makeText(context, "Contact Us clicked", Toast.LENGTH_SHORT).show()
        }

        binding.signOutButton.setOnClickListener {
            auth.signOut()
            Toast.makeText(context, "Signed Out", Toast.LENGTH_SHORT).show()

            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }


    /**
     * 辅助方法，用于导航到另一个Fragment
     * @param fragment 要导航到的目标Fragment实例
     */
    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null) // 将上一个Fragment添加到返回栈
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



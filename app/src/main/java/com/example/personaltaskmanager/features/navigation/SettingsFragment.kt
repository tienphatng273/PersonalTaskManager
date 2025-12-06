package com.example.personaltaskmanager.features.navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.personaltaskmanager.R
// DÒNG IMPORT GÂY LỖI ĐÃ BỊ XÓA HOẶC BỊ COMMENT

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.feature_settings_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // THÊM LOGIC: Gán giá trị, Icon và xử lý click cho các mục
        setupSettingItem(view, R.id.setting_profile_container,
            R.drawable.feature_task_manager_ic_image_placeholder, // Dùng icon Placeholder tạm thời
            "Quản lý Hồ sơ",
            "Cập nhật thông tin cá nhân và mật khẩu") {
            // TODO: Thay thế bằng Intent chính xác đến Profile Settings
            // val intent = Intent(requireContext(), ProfileSettingsActivity::class.java)
            // startActivity(intent)
        }

        setupSettingItem(view, R.id.setting_logout,
            android.R.drawable.ic_menu_close_clear_cancel,
            "Đăng xuất",
            "Thoát khỏi tài khoản hiện tại") {
            // TODO: Xử lý Đăng xuất
        }

        // Mục Theme (dùng switch)
        setupSettingItem(view, R.id.setting_theme,
            android.R.drawable.ic_menu_view,
            "Chế độ Sáng/Tối",
            "Tùy chỉnh giao diện ứng dụng") {
            // Logic cho switch sẽ được xử lý riêng nếu cần
        }

        setupSettingItem(view, R.id.setting_language,
            android.R.drawable.ic_menu_compass, // Icon la bàn tạm
            "Ngôn ngữ",
            "Tiếng Việt (Mặc định)") {
            // TODO: Mở màn hình chọn Ngôn ngữ
        }
    }

    // Hàm tiện ích để gán giá trị runtime cho các mục Settings
    private fun setupSettingItem(
        parentView: View,
        containerId: Int,
        iconRes: Int,
        title: String,
        subtitle: String,
        onClick: () -> Unit
    ) {
        val container = parentView.findViewById<View>(containerId)
        if (container != null) {
            container.findViewById<ImageView>(R.id.iv_icon)?.setImageResource(iconRes)
            container.findViewById<TextView>(R.id.tv_title)?.text = title
            container.findViewById<TextView>(R.id.tv_subtitle)?.text = subtitle
            container.setOnClickListener { onClick() }
        }
    }
}
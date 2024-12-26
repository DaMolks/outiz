package com.example.outiz.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.outiz.databinding.ItemModuleBinding
import com.example.outiz.models.Module

class ModulesAdapter(
    private val modules: List<Module>,
    private val onModuleClick: (Module) -> Unit
) : RecyclerView.Adapter<ModulesAdapter.ModuleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val binding = ItemModuleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ModuleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        holder.bind(modules[position])
    }

    override fun getItemCount() = modules.size

    inner class ModuleViewHolder(private val binding: ItemModuleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(module: Module) {
            with(binding) {
                moduleIcon.setImageResource(module.iconRes)
                moduleTitle.text = module.title
                moduleDescription.text = module.description
                root.isEnabled = module.isEnabled
                root.alpha = if (module.isEnabled) 1.0f else 0.5f

                if (module.isEnabled) {
                    root.setOnClickListener { onModuleClick(module) }
                } else {
                    root.setOnClickListener(null)
                }
            }
        }
    }
}
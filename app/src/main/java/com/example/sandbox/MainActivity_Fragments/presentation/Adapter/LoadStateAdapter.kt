package com.example.sandbox.MainActivity_Fragments.presentation.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sandbox.databinding.ProgressBarForLoadStateAdapterBinding

class LoadStateAdapter(private val retry: () -> Unit): LoadStateAdapter<com.example.sandbox.MainActivity_Fragments.presentation.Adapter.LoadStateAdapter.LoadStateViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = ProgressBarForLoadStateAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: ProgressBarForLoadStateAdapterBinding) :
             RecyclerView.ViewHolder(binding.root){

         init {
             binding.retryButtonLoadState.setOnClickListener{
                 retry.invoke()
             }
         }

                 fun bind(loadState: LoadState){
                     binding.apply {
                         progressBarLoadState.isVisible = loadState is LoadState.Loading
                         progressBarLoadState.isVisible = loadState !is LoadState.Loading
                         textViewErrorLoadState.isVisible = loadState !is LoadState.Loading
                     }
                 }

             }
}
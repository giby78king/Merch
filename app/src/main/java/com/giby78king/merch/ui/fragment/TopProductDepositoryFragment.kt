package com.giby78king.merch.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.giby78king.merch.Function.SpinnerSet
import com.giby78king.merch.Model.ProductDepository
import com.giby78king.merch.Model.ProductDepository.Companion.ProductDepositoryList
import com.giby78king.merch.Model.TextAmountSetting
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmTopProductDepositoryViewModel
import com.giby78king.merch.ViewModel.VmTopProductDepositoryViewModel.Companion.selectChannel
import com.giby78king.merch.ViewModel.VmTopProductDepositoryViewModel.Companion.selectMember
import com.giby78king.merch.ViewModel.VmProductDepositoryViewModel


class TopProductDepositoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_top_productdepository, container, false)

        val vmProductDepositoryViewModel =
            ViewModelProvider(requireActivity()).get(VmProductDepositoryViewModel::class.java)
        vmProductDepositoryViewModel.getDatas("")
        vmProductDepositoryViewModel.productDepositoryDatas.observe(viewLifecycleOwner) { it ->
            val filterStockDepositoryList = mutableListOf<ProductDepository>()
            ProductDepositoryList.filter { it.holdingAmount != 0 }.forEach {
                filterStockDepositoryList.add(it)
            }

            val vmCostStockDepositoryViewModel =
                ViewModelProvider(requireActivity()).get(VmTopProductDepositoryViewModel::class.java)

            val txtHoldingAmount = root.findViewById<TextView>(R.id.txtHoldingAmount)
            val txtTotalCost = root.findViewById<TextView>(R.id.txtTotalCost)
            val imgMember: ImageView = root.findViewById(R.id.imgChannelDetail)
            val imgChannel: ImageView = root.findViewById(R.id.imgChannel)
            val linearMember: LinearLayout = root.findViewById(R.id.linearMember)
            val linearChannel: LinearLayout = root.findViewById(R.id.linearChannel)
            val btnToDetail: Button = root.findViewById(R.id.btnToDetail)

            vmCostStockDepositoryViewModel.memberTopInfo.observe(viewLifecycleOwner) { clickData ->
                if (selectMember.id.isNotEmpty()) {
                    linearMember.isVisible = true
                    //Icon相關
                    val module = "member"
                    val icon = selectMember.icon
                    SpinnerSet().setSpinnerIcon(module, icon, imgMember, resources)
                } else {
                    linearMember.isVisible = false
                }

                linearMember.setOnClickListener {
                    selectMember.id = ""
                    linearMember.isVisible = false
                    if (selectChannel.id.isNotEmpty()) {
                        linearChannel.isVisible = true
                        //Icon相關
                        val module = "channeldetail"
                        val icon = selectChannel.icon
                        SpinnerSet().setSpinnerIcon(module, icon, imgChannel, resources)
                    }

                    if (ProductDepositoryList.count() > 0) {
                        var filterList = mutableListOf<ProductDepository>()

                        if (selectMember.id.isNotEmpty()) {
                            filterList = ProductDepositoryList.filter {
                                it.group.contains(selectMember.belong) && it.member.contains(selectMember.id)
                            }.toMutableList()

                            if (selectChannel.id.isNotEmpty()) {
                                filterList = filterList.filter {
                                    it.channelDetail == selectChannel.id
                                }.toMutableList()
                            }
                        }

                        if (selectChannel.id.isNotEmpty()) {
                            filterList = ProductDepositoryList.filter {
                                it.channelDetail == selectChannel.id
                            }.toMutableList()
                            if (selectMember.id.isNotEmpty()) {
                                filterList = filterList.filter {
                                    it.group.contains(selectMember.belong) && it.member.contains(selectMember.id)
                                }.toMutableList()
                            }
                        }

                        if (filterList.count() > 0) {
                            txtHoldingAmount.text =
                                TextAmountSetting().formatAmountNoDollar(filterList[0].holdingAmount.toString())
                            txtTotalCost.text =
                                TextAmountSetting().formatAmountNoDollar(filterList[0].cost.toString())
                        } else {
                            txtHoldingAmount.text = "0"
                            txtTotalCost.text = "0"
                        }
                    }
                }

                if (selectChannel.id.isNotEmpty()) {
                    linearChannel.isVisible = true
                    //Icon相關
                    val module = "channeldetail"
                    val icon = selectChannel.icon
                    SpinnerSet().setSpinnerIcon(module, icon, imgChannel, resources)
                } else {
                    linearChannel.isVisible = false
                }

                linearChannel.setOnClickListener {
                    selectChannel.id = ""
                    linearChannel.isVisible = false
                    if (selectMember.id.isNotEmpty()) {
                        linearMember.isVisible = true
                        //Icon相關
                        val module = "member"
                        val icon = selectMember.icon
                        SpinnerSet().setSpinnerIcon(module, icon, imgMember, resources)
                    }

                    if (ProductDepositoryList.count() > 0) {
                        var filterList = mutableListOf<ProductDepository>()

                        if (selectMember.id.isNotEmpty()) {
                            filterList = ProductDepositoryList.filter {
                                it.group.contains(selectMember.belong) && it.member.contains(selectMember.id)
                            }.toMutableList()

                            if (selectChannel.id.isNotEmpty()) {
                                filterList = filterList.filter {
                                    it.channelDetail == selectChannel.id
                                }.toMutableList()
                            }
                        }

                        if (selectChannel.id.isNotEmpty()) {
                            filterList = ProductDepositoryList.filter {
                                it.channelDetail == selectChannel.id
                            }.toMutableList()
                            if (selectMember.id.isNotEmpty()) {
                                filterList = filterList.filter {
                                    it.group.contains(selectMember.belong) && it.member.contains(selectMember.id)
                                }.toMutableList()
                            }
                        }

                        if (filterList.count() > 0) {
                            txtHoldingAmount.text =
                                TextAmountSetting().formatAmountNoDollar(filterList[0].holdingAmount.toString())
                            txtTotalCost.text =
                                TextAmountSetting().formatAmountNoDollar(filterList[0].cost.toString())
                        } else {
                            txtHoldingAmount.text = "0"
                            txtTotalCost.text = "0"
                        }
                    }
                }

                if (ProductDepositoryList.count() > 0) {
                    var filterList = mutableListOf<ProductDepository>()

                    if (selectMember.id.isNotEmpty()) {
                        filterList = ProductDepositoryList.filter {
                            it.group.contains(selectMember.belong) && it.member.contains(selectMember.id)
                        }.toMutableList()

                        if (selectChannel.id.isNotEmpty()) {
                            filterList = filterList.filter {
                                it.channelDetail == selectChannel.id
                            }.toMutableList()
                        }
                    }

                    if (selectChannel.id.isNotEmpty()) {
                        filterList = ProductDepositoryList.filter {
                            it.channelDetail == selectChannel.id
                        }.toMutableList()
                        if (selectMember.id.isNotEmpty()) {
                            filterList = filterList.filter {
                                it.group.contains(selectMember.belong) && it.member.contains(selectMember.id)
                            }.toMutableList()
                        }
                    }

                    if (filterList.count() > 0) {
                        txtHoldingAmount.text =
                            TextAmountSetting().formatAmountNoDollar(filterList[0].holdingAmount.toString())
                        txtTotalCost.text =
                            TextAmountSetting().formatAmountNoDollar(filterList[0].cost.toString())
                    } else {
                        txtHoldingAmount.text = "0"
                        txtTotalCost.text = "0"
                    }
                }
            }

            vmCostStockDepositoryViewModel.channelDetailTopInfo.observe(viewLifecycleOwner) { clickData ->
                if (selectMember.id.isNotEmpty()) {
                    linearMember.isVisible = true
                    //Icon相關
                    val module = "member"
                    val icon = selectMember.icon
                    SpinnerSet().setSpinnerIcon(module, icon, imgMember, resources)
                } else {
                    linearMember.isVisible = false
                }

                linearMember.setOnClickListener {
                    selectMember.id = ""
                    linearMember.isVisible = false
                    if (selectChannel.id.isNotEmpty()) {
                        linearChannel.isVisible = true
                        //Icon相關
                        val module = "channeldetail"
                        val icon = selectChannel.icon
                        SpinnerSet().setSpinnerIcon(module, icon, imgChannel, resources)
                    }

                    if (ProductDepositoryList.count() > 0) {
                        var filterList = mutableListOf<ProductDepository>()

                        if (selectMember.id.isNotEmpty()) {
                            filterList = ProductDepositoryList.filter {
                                it.group.contains(selectMember.belong) && it.member.contains(selectMember.id)
                            }.toMutableList()

                            if (selectChannel.id.isNotEmpty()) {
                                filterList = filterList.filter {
                                    it.channelDetail == selectChannel.id
                                }.toMutableList()
                            }
                        }

                        if (selectChannel.id.isNotEmpty()) {
                            filterList = ProductDepositoryList.filter {
                                it.channelDetail == selectChannel.id
                            }.toMutableList()
                            if (selectMember.id.isNotEmpty()) {
                                filterList = filterList.filter {
                                    it.group.contains(selectMember.belong) && it.member.contains(selectMember.id)
                                }.toMutableList()
                            }
                        }

                        if (filterList.count() > 0) {
                            txtHoldingAmount.text =
                                TextAmountSetting().formatAmountNoDollar(filterList[0].holdingAmount.toString())
                            txtTotalCost.text =
                                TextAmountSetting().formatAmountNoDollar(filterList[0].cost.toString())
                        } else {
                            txtHoldingAmount.text = "0"
                            txtTotalCost.text = "0"
                        }
                    }
                }

                if (selectChannel.id.isNotEmpty()) {
                    linearChannel.isVisible = true
                    //Icon相關
                    val module = "channeldetail"
                    val icon = selectChannel.icon
                    SpinnerSet().setSpinnerIcon(module, icon, imgChannel, resources)
                } else {
                    linearChannel.isVisible = false
                }

                linearChannel.setOnClickListener {
                    selectChannel.id = ""
                    linearChannel.isVisible = false
                    if (selectMember.id.isNotEmpty()) {
                        linearMember.isVisible = true
                        //Icon相關
                        val module = "member"
                        val icon = selectMember.icon
                        SpinnerSet().setSpinnerIcon(module, icon, imgMember, resources)
                    }

                    if (ProductDepositoryList.count() > 0) {
                        var filterList = mutableListOf<ProductDepository>()

                        if (selectMember.id.isNotEmpty()) {
                            filterList = ProductDepositoryList.filter {
                                it.group.contains(selectMember.belong) && it.member.contains(selectMember.id)
                            }.toMutableList()

                            if (selectChannel.id.isNotEmpty()) {
                                filterList = filterList.filter {
                                    it.channelDetail == selectChannel.id
                                }.toMutableList()
                            }
                        }

                        if (selectChannel.id.isNotEmpty()) {
                            filterList = ProductDepositoryList.filter {
                                it.channelDetail == selectChannel.id
                            }.toMutableList()
                            if (selectMember.id.isNotEmpty()) {
                                filterList = filterList.filter {
                                    it.group.contains(selectMember.belong) && it.member.contains(selectMember.id)
                                }.toMutableList()
                            }
                        }

                        if (filterList.count() > 0) {
                            txtHoldingAmount.text =
                                TextAmountSetting().formatAmountNoDollar(filterList[0].holdingAmount.toString())
                            txtTotalCost.text =
                                TextAmountSetting().formatAmountNoDollar(filterList[0].cost.toString())
                        } else {
                            txtHoldingAmount.text = "0"
                            txtTotalCost.text = "0"
                        }
                    }
                }

                if (ProductDepositoryList.count() > 0) {
                    var filterList = mutableListOf<ProductDepository>()

                    if (selectMember.id.isNotEmpty()) {
                        filterList = ProductDepositoryList.filter {
                            it.group.contains(selectMember.belong) && it.member.contains(selectMember.id)
                        }.toMutableList()

                        if (selectChannel.id.isNotEmpty()) {
                            filterList = filterList.filter {
                                it.channelDetail == selectChannel.id
                            }.toMutableList()
                        }
                    }

                    if (selectChannel.id.isNotEmpty()) {
                        filterList = ProductDepositoryList.filter {
                            it.channelDetail == selectChannel.id
                        }.toMutableList()
                        if (selectMember.id.isNotEmpty()) {
                            filterList = filterList.filter {
                                it.group.contains(selectMember.belong) && it.member.contains(selectMember.id)
                            }.toMutableList()
                        }
                    }

                    if (filterList.count() > 0) {
                        txtHoldingAmount.text =
                            TextAmountSetting().formatAmountNoDollar(filterList[0].holdingAmount.toString())
                        txtTotalCost.text =
                            TextAmountSetting().formatAmountNoDollar(filterList[0].cost.toString())
                    } else {
                        txtHoldingAmount.text = "0"
                        txtTotalCost.text = "0"
                    }
                }
            }

            btnToDetail.setOnClickListener {
                val vmTopStockDepositoryViewModel: VmTopProductDepositoryViewModel =
                    ViewModelProvider(requireActivity())[VmTopProductDepositoryViewModel::class.java]
                vmTopStockDepositoryViewModel.setDepoToDetailDatas("trigger")

                val firstViewPager =
                    root.rootView.findViewById(R.id.view_pager) as ViewPager
                firstViewPager.currentItem = 2
            }
        }
        return root
    }
}
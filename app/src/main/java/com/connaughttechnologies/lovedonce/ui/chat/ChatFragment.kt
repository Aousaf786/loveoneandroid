package com.connaughttechnologies.lovedonce.ui.chat

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.data.models.requests.RequestGetServiceToken
import com.connaughttechnologies.lovedonce.data.models.responses.ResponseGetServiceToken
import com.connaughttechnologies.lovedonce.databinding.FragmentChatBinding
import com.connaughttechnologies.lovedonce.extensions.getValue
import com.connaughttechnologies.lovedonce.ui.chat.adapter.ChatAdapter
//import com.connaughttechnologies.lovedonce.ui.chat.module.getChannel
//import com.twilio.chat.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


class ChatFragment : BaseFragment<ChatViewModel, FragmentChatBinding>() {
    override var viewModelClass: Class<ChatViewModel> = ChatViewModel::class.java
    override var layoutId: Int = R.layout.fragment_chat
    override var bindingVariable: Int = BR.viewModel

//    private lateinit var chatClient: ChatClient
    private lateinit var response: ResponseGetServiceToken

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel.eventExeApiGetServiceToken.value = RequestGetServiceToken()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (sharedViewModel.isGroupChat) {
            binding.toolbar.tvToolbarTitle.text = "Group Chat"
        } else
            binding.toolbar.tvToolbarTitle.text = "Admin"
        binding.toolbar.sharedViewModel = sharedViewModel
    }

    override fun observeSharedViewModel() {
        super.observeSharedViewModel()
        if (sharedViewModel.isGroupChat) {
            return
        }
        sharedViewModel.liveServiceToken.observe(viewLifecycleOwner, Observer {
            response = it
//            initChat(it)
        })
    }

/*
    private fun initChat(response: ResponseGetServiceToken) {
        sharedViewModel.showSharedLoading()
        val properties = ChatClient.Properties.Builder().setRegion("us1").createProperties()
        ChatClient.create(
            requireContext(),
            response.token,
            properties,
            object : CallbackListener<ChatClient>() {
                override fun onSuccess(chatClient: ChatClient) {
                    this@ChatFragment.chatClient = chatClient
                    chatClient.addListener(object : ChatClientListener {
                        override fun onChannelJoined(p0: Channel?) {

                        }

                        override fun onChannelInvited(p0: Channel?) {
                        }

                        override fun onChannelAdded(p0: Channel?) {
                        }

                        override fun onChannelUpdated(p0: Channel?, p1: Channel.UpdateReason?) {
                        }

                        override fun onChannelDeleted(p0: Channel?) {
                        }

                        override fun onChannelSynchronizationChange(p0: Channel?) {
                        }

                        override fun onError(p0: ErrorInfo?) {
                        }

                        override fun onUserUpdated(p0: User?, p1: User.UpdateReason?) {
                        }

                        override fun onUserSubscribed(p0: User?) {
                        }

                        override fun onUserUnsubscribed(p0: User?) {
                        }

                        override fun onClientSynchronization(status: ChatClient.SynchronizationStatus?) {
                            if (status == ChatClient.SynchronizationStatus.COMPLETED) {
                                // Client is now ready for business, start working
                                lifecycleScope.launch {
                                    chatClient.channels.channelBuilder()
                                        .withFriendlyName(response.channelFriendlyName)
                                        .withUniqueName(response.channelUniqueName)
                                        .withType(Channel.ChannelType.PRIVATE).build(object :
                                            CallbackListener<Channel>() {
                                            override fun onSuccess(channel: Channel?) {
                                                channel!!.join(object : StatusListener() {
                                                    override fun onSuccess() {
                                                        fetchMessages(chatClient, channel)
                                                        initSendMessage(channel)
                                                    }
                                                })
                                                channel.members.inviteByIdentity(
                                                    response.adminIdentity,
                                                    object :
                                                        StatusListener() {
                                                        override fun onSuccess() {
                                                            Timber.d("Channel inviteByIdentity: onSuccess ")

                                                        }

                                                    })

                                            }

                                            override fun onError(errorInfo: ErrorInfo?) {
                                                super.onError(errorInfo)
                                            }
                                        })
                                    chatClient.channels.getUserChannelsList(object :
                                        CallbackListener<Paginator<ChannelDescriptor>>() {
                                        override fun onSuccess(channelPaginator: Paginator<ChannelDescriptor>?) {
                                            for (channel in channelPaginator!!.items) {
                                                Timber.d("Channel named: " + channel.friendlyName)
                                                if (channel.uniqueName == response.channelUniqueName) {
                                                    channel.getChannel(object :
                                                        CallbackListener<Channel>() {
                                                        override fun onSuccess(channel: Channel?) {
                                                            Timber.d("Channel named: " + channel!!.status)
                                                            fetchMessages(chatClient, channel)
                                                            initSendMessage(channel)
                                                            channel.members.inviteByIdentity(
                                                                response.adminIdentity,
                                                                object :
                                                                    StatusListener() {
                                                                    override fun onSuccess() {
                                                                        Timber.d("Channel inviteByIdentity: onSuccess ")

                                                                    }

                                                                })
                                                        }

                                                    })
                                                }

                                            }
                                        }

                                    })
//                                    chatClient.channels.getChannel(response.channelUniqueName).messages.getLastMessages(
//                                        50,
//                                        object :
//                                            CallbackListener<List<Message>>() {
//                                            override fun onSuccess(list: List<Message>?) {
//                                                Timber.d("${list?.size}")
//                                            }
//
//                                        })
                                }
                            }
                        }

                        override fun onNewMessageNotification(p0: String?, p1: String?, p2: Long) {
                        }

                        override fun onAddedToChannelNotification(p0: String?) {
                        }

                        override fun onInvitedToChannelNotification(p0: String?) {
                        }

                        override fun onRemovedFromChannelNotification(p0: String?) {
                        }

                        override fun onNotificationSubscribed() {
                        }

                        override fun onNotificationFailed(p0: ErrorInfo?) {
                        }

                        override fun onConnectionStateChange(p0: ChatClient.ConnectionState?) {
                        }

                        override fun onTokenExpired() {
                        }

                        override fun onTokenAboutToExpire() {
                        }
                    })
                }

                override fun onError(errorInfo: ErrorInfo) {

                }
            })
    }
*/

/*
    private fun fetchMessages(chatClient: ChatClient, channel: Channel) {
        lifecycleScope.launch {
            chatClient.channels.getChannel(channel.uniqueName).messages.getLastMessages(
                500,
                object :
                    CallbackListener<List<Message>>() {
                    override fun onSuccess(list: List<Message>?) {
                        sharedViewModel.hideSharedLoading()
                        Timber.d("${list?.size}")
                        val adapter = ChatAdapter(response, list!!)
                        binding.rvChat.adapter = adapter
                        lifecycleScope.launch(Dispatchers.Main) {
                            if (list.isNotEmpty())
                                binding.rvChat.smoothScrollToPosition(list.lastIndex)
                        }
                    }

                    override fun onError(errorInfo: ErrorInfo?) {
                        super.onError(errorInfo)
                        sharedViewModel.hideSharedLoading()
                    }
                })
        }
        channel.addListener(object : ChannelListener {
            override fun onMessageAdded(message: Message?) {
                Timber.d("${message!!.messageBody}")
                fetchMessages(chatClient, channel)

            }

            override fun onMessageUpdated(p0: Message?, p1: Message.UpdateReason?) {
            }

            override fun onMessageDeleted(p0: Message?) {
            }

            override fun onMemberAdded(p0: Member?) {
            }

            override fun onMemberUpdated(p0: Member?, p1: Member.UpdateReason?) {
            }

            override fun onMemberDeleted(p0: Member?) {
            }

            override fun onTypingStarted(p0: Channel?, p1: Member?) {
            }

            override fun onTypingEnded(p0: Channel?, p1: Member?) {
            }

            override fun onSynchronizationChanged(p0: Channel?) {
            }

        })
    }
*/

/*
    private fun initSendMessage(channel: Channel) {
        binding.ivSend.setOnClickListener {
            val options = Message.options().withBody(binding.etChat.getValue())
            channel.messages.sendMessage(options, object : CallbackListener<Message>() {
                override fun onSuccess(p0: Message?) {
                    Timber.d("sendMessage ")
                    binding.etChat.setText("")
                    fetchMessages(chatClient, channel)
                }

            })
        }
    }
*/
}
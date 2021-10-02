package com.example.myHealth;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.opentok.android.Connection;
import com.opentok.android.OpentokError;
import com.opentok.android.Session;
import com.opentok.android.Stream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String SIGNAL_TYPE = "text-signal";

    private Session session;
    private SignalMessageAdapter messageHistory;

    private EditText messageEditTextView;
    private ListView messageHistoryListView;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //anonymous inner class
    private Session.SessionListener sessionListener = new Session.SessionListener() {
        @Override
        public void onConnected(Session session) {
            Log.i(TAG, "Session Connected");
            messageEditTextView.setEnabled(true);
        }

        @Override
        public void onDisconnected(Session session) {
            Log.i(TAG, "Session Disconnected");
        }

        @Override
        public void onStreamReceived(Session session, Stream stream) {
            Log.i(TAG, "Stream Received");
        }

        @Override
        public void onStreamDropped(Session session, Stream stream) {
            Log.i(TAG, "Stream Dropped");
        }

        @Override
        public void onError(Session session, OpentokError opentokError) {
            finishWithMessage("Session error: " + opentokError.getMessage());
        }
    };

    //anonymous inner class
    private Session.SignalListener signalListener = new Session.SignalListener() {
        @Override
        public void onSignalReceived(Session session, String type, String data, Connection connection) {

            boolean remote = !connection.equals(session.getConnection());
            if (type != null && type.equals(SIGNAL_TYPE)) {
                showMessage(data, remote);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if(!OpentokConfig.isValid()) {
            finishWithMessage("Invalid OpenTokConfig. " + OpentokConfig.getDescription());
            return;
        }

        session = new Session.Builder(this.getContext(), OpentokConfig.API_KEY, OpentokConfig.SESSION_ID).build();
        session.setSessionListener(sessionListener);
        session.setSignalListener(signalListener);
        session.connect(OpentokConfig.TOKEN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        messageEditTextView = view.findViewById(R.id.message_edit_text);
        messageHistoryListView = view.findViewById(R.id.message_history_list_view);

        // Attach data source to message history
        messageHistory = new SignalMessageAdapter(this.getContext());
        messageHistoryListView.setAdapter(messageHistory);

        // Attach handlers to UI
        messageEditTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            //called when an action is performed
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    // Hides the soft keyboard after typing and pressing enter
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    sendMessage();
                    return true;
                }
                return false;
            }
        });

        messageEditTextView.setEnabled(false);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        if (session != null) {
            session.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (session != null) {
            session.onResume();
        }
    }

    private void sendMessage() {
        Log.d(TAG, "Send Message");

        SignalMessage signal = new SignalMessage(messageEditTextView.getText().toString());
        session.sendSignal(SIGNAL_TYPE, signal.getMessageText());

        messageEditTextView.setText("");
    }

    private void showMessage(String messageData, boolean remote) {
        Log.d(TAG, "Show Message");

        SignalMessage message = new SignalMessage(messageData, remote);
        messageHistory.add(message);
    }


    private void finishWithMessage(String message) {
        Log.e(TAG, message);
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
        getActivity().finish();
    }
}
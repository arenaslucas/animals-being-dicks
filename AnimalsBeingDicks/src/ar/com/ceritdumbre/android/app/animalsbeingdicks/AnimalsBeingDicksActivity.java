package ar.com.ceritdumbre.android.app.animalsbeingdicks;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class AnimalsBeingDicksActivity extends Activity {

	private TextView titleTextView;
	private TextView linkTextView;
	private WebView descriptionWebView;
	private TextView dateTextView;
	private Button anotherButton;
	private Button checkForNewPostButton;
	private Button exitButton;

	private static final String RSS_URL = "http://animalsbeingdicks.com/rss";
	private static final String ENCODING = "UTF-8";
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\""
			+ ENCODING + "\" ?>";
	private static final String CONTENT_TYPE = "text/html";

	private AndroidSaxFeedParser parser;
	
	private List<Message> messages;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		titleTextView = (TextView) findViewById(R.id.title_TextView);
		linkTextView = (TextView) findViewById(R.id.link_TextView);
		descriptionWebView = (WebView) findViewById(R.id.description_WebView);
		dateTextView = (TextView) findViewById(R.id.date_TextView);
		anotherButton = (Button) findViewById(R.id.another_Button);
		exitButton = (Button) findViewById(R.id.exit_Button);
		checkForNewPostButton = (Button) findViewById(R.id.check_for_new_post_Button);

		anotherButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Message randomMessage = readRandomMessage(messages);
				showMessage(randomMessage);
			}
		});
		
		checkForNewPostButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				messages = feedMessages();
				Message randomMessage = readRandomMessage(messages);
				showMessage(randomMessage);
			}
		});

		exitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		messages = feedMessages();
		Message randomMessage = readRandomMessage(messages);
		showMessage(randomMessage);

	}

	private List<Message> feedMessages() {
		List<Message> messages = null;
		parser = new AndroidSaxFeedParser(RSS_URL);
		messages = parser.parse();
		return messages;
	}

	private Message readRandomMessage(List<Message> messages) {
		int randomIndex = (int) (Math.random() * messages.size());
		return messages.get(randomIndex);
	}

	private void showMessage(Message message) {
		titleTextView.setText(message.getTitle());
		linkTextView.setText(message.getLink().toString());
		dateTextView.setText(message.getDate());
		
		/**
		 * Este seteo hace que la WebView se adapte a la pantalla (en cuanto al ancho)
		 * y no sea necesario scrollear
		 */
		descriptionWebView.getSettings().setLayoutAlgorithm(
				LayoutAlgorithm.SINGLE_COLUMN);
		
		
		descriptionWebView.loadData(XML_HEADER + message.getDescription(),
				CONTENT_TYPE, ENCODING);

	}
}
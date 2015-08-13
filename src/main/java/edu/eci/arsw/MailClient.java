package edu.eci.arsw;

import edu.eci.arsw.util.Maybe;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Clock;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MailClient extends JFrame {

	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final int SMTP_HOST_PORT = 465;

	private JPanel contentPane;
	private JTextField recipients;
	private JTextField subject;
	private JLabel lblSubject;
	private JTextArea body;
	private JFrame self=this;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MailClient frame = new MailClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MailClient() {
		setTitle("My Gmail Mail Sender");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 569, 469);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		recipients = new JTextField();
		recipients.setBounds(101, 29, 428, 28);
		contentPane.add(recipients);
		recipients.setColumns(10);

		JLabel lblTo = new JLabel("To");
		lblTo.setBounds(28, 35, 61, 16);
		contentPane.add(lblTo);

		subject = new JTextField();
		subject.setColumns(10);
		subject.setBounds(101, 63, 428, 28);
		contentPane.add(subject);

		lblSubject = new JLabel("Subject");
		lblSubject.setBounds(28, 69, 61, 16);
		contentPane.add(lblSubject);

		JScrollPane bodySp = new JScrollPane();
		bodySp.setBounds(28, 131, 501, 244);
		contentPane.add(bodySp);

		body = new JTextArea();
		bodySp.setViewportView(body);

		
		final WordAssistant sc=new WordAssistant();
		
                /*
                body.getDocument().addDocumentListener(new DocumentListener() {

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        update(e);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        update(e);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        update(e);
                    }
                    
                    private void update(DocumentEvent e) {
                        DocumentEvent.EventType type = e.getType();
                        DocumentEvent.ElementChange change = e.getChange();
                        String cnt=body.getText();
                        int lastspace=cnt.lastIndexOf(" ", pos);
                        final String word;
                        if (lastspace==-1){
                           word=cnt.substring(0,pos);	
                        }
                        else {
                                word=cnt.substring(lastspace+1,pos);	
                        }
                        final Maybe<String> replacement=sc.check(word);
                        System.err.println("Pos: "+pos+" lastspace: "+lastspace+" word: "+word+" -> "+replacement+" " +cnt);

                        if (replacement.isJust()){
                                // final String _word=word;
                                // final Maybe<String> _replacement=replacement;
                                SwingUtilities.invokeLater(new Runnable()
                                {
                                    @Override
                                    public void run(){
                                        body.setText(body.getText().replace("\b"+word+"\b", replacement.getValue()));
                                    }
                                });
                        }								
                        
                    }
                }
                );
                */

                body.addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
				int pos=e.getDot();
				String cnt=body.getText();
				int lastspace=cnt.lastIndexOf(" ", pos);
				final String word;
				if (lastspace==-1){
			           word=cnt.substring(0,pos);	
				}
                                else {
					word=cnt.substring(lastspace+1,pos);	
				}
                                final Maybe<String> replacement=sc.check(word);
				
				if (replacement.isJust()){
					// final String _word=word;
					// final Maybe<String> _replacement=replacement;
					SwingUtilities.invokeLater(new Runnable()
					{
                                            @Override
					    public void run(){
						body.setText(body.getText().replace("\b"+word+"\b", replacement.getValue()));
					    }
					});
				}								
			}
		});
		
		JButton btnSendEmail = new JButton("Send email");
		btnSendEmail.setBounds(233, 397, 117, 29);
		contentPane.add(btnSendEmail);

		btnSendEmail.addActionListener(new ActionListener() {

			// http://www.regular-expressions.info/creditcard.html

			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = body.getText();
				if (!Pattern.compile("^Buen(a|o)s( )*(dias|tardes|noches)*")
						.matcher(msg).find()) {
					msg = "Buen día!,\n" + msg;
				}

				if (!Pattern
					.compile("(Cordialmente|Atentamente|Saludos|Con gusto)")
					.matcher(msg).find()) {
				    msg = msg + "Atentamente, Juan Perez";
				}

				Properties props = new Properties();

				props.put("mail.transport.protocol", "smtps");
				props.put("mail.smtps.host", SMTP_HOST_NAME);
				props.put("mail.smtps.auth", "true");

				Session mailSession = Session.getDefaultInstance(props);
				mailSession.setDebug(true);
				Transport transport;
				try {
					transport = mailSession.getTransport();
					MimeMessage message = new MimeMessage(mailSession);
					message.setSubject(subject.getText());
					message.setContent(msg, "text/plain");

					message.addRecipient(Message.RecipientType.TO,
							new InternetAddress(recipients.getText()));

					transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT,
							"user@gmail.com", "password");

					transport.sendMessage(message,
							message.getRecipients(Message.RecipientType.TO));
					transport.close();
				} catch (NoSuchProviderException e1) {
					JOptionPane.showMessageDialog(self,
							"Error:" + e1.getLocalizedMessage());
				} catch (AuthenticationFailedException e1) {
					JOptionPane.showMessageDialog(self,
							"Invalid user or password on SMTP configuration.");
				} catch (MessagingException e1) {
					JOptionPane.showMessageDialog(self,
							"Error:" + e1.getLocalizedMessage());
				}

			}

		});


	}
}

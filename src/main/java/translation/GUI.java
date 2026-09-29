package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.Arrays;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new JSONTranslator();

            // LANGUAGE PANEL
            JPanel languagePanel = new JPanel();

            // LANGUAGE LABEL
            languagePanel.add(new JLabel("Language:"));

            // LANGUAGE COMBO BOX
            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String countryCode : translator.getLanguageCodes()) {
                LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();

                languageComboBox.addItem(languageCodeConverter.fromLanguageCode(countryCode));
            }
            languagePanel.add(languageComboBox);

            // TRANSLATION PANEL
            JPanel buttonPanel = new JPanel();
            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);

            // Country List Selector
            String[] items = new String[translator.getCountryCodes().size()];
            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
            LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();

            int i = 0;
            for(String countryCode : translator.getCountryCodes()) {
                items[i++] =  countryCodeConverter.fromCountryCode(countryCode);
            }

            // Country Panel
            JPanel countryPanel = new JPanel();
            JList<String> list = new JList<>(items);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            // place the JList in a scroll pane so that it is scrollable in the UI
            JScrollPane scrollPane = new JScrollPane(list);
            countryPanel.add(scrollPane);

            list.addListSelectionListener(new ListSelectionListener() {

                /**
                 * Called whenever the value of the selection changes.
                 *
                 * @param e the event that characterizes the change.
                 */
                @Override
                public void valueChanged(ListSelectionEvent e) {

                    String selectedCountry = list.getSelectedValue();
                    String selectedLanguage = languageComboBox.getSelectedItem().toString();
                    String countryCode = countryCodeConverter.fromCountry(selectedCountry);
                    String languageCode = languageCodeConverter.fromLanguage(selectedLanguage);
                    String translated = translator.translate(countryCode, languageCode);

                    resultLabel.setText(translated);

                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}

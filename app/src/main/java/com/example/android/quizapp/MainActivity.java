package com.example.android.quizapp;

//import android.provider.MediaStore;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.quizapp.R;

public class MainActivity extends AppCompatActivity {
    // Variable for the total score of the entire quiz
    int TotalScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //If the phone is in landscape mode, the 'full • entertainment' text will be stretched
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            TextView mcuHeading = (TextView) findViewById(R.id.full_entertainment_text_view);
            mcuHeading.setTextScaleX((float) 1.6);
        }  // set to what ever fits

        //Certain text is moved to a new line when the phone is in portrait mode
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Portrait Mode
            // Each TextView uses the alternative text in the strings.xml when in portrait mode
            TextView q3TextViewNextLine = (TextView) findViewById(R.id.question_3_text_view);
            q3TextViewNextLine.setText(getString(R.string.question3_linebreak));

            TextView q5TextViewNextLine = (TextView) findViewById(R.id.question_5_text_view);
            q5TextViewNextLine.setText(getString(R.string.question5_linebreak));

            TextView q6TextViewNextLine = (TextView) findViewById(R.id.question_6_text_view);
            q6TextViewNextLine.setText(getString(R.string.question6_linebreak));
        }
    }

    /**
     * @param view USer clicks the Submit button,
     *             if everything is correctly completed a toast message appears with the user's final score
     */
    public void submitAnswers(View view) {

        RadioGroup checkQ1RadioButtons = (RadioGroup) findViewById(R.id.q1_radio_group);
        RadioGroup checkQ2RadioButtons = (RadioGroup) findViewById(R.id.q2_radio_group);
        RadioGroup checkQ3RadioButtons = (RadioGroup) findViewById(R.id.q3_radio_group);

        //User selected a radio button for question 1
        RadioButton twoEyesRadioButton = (RadioButton) findViewById(R.id.two_eyes_radio);
        boolean choseTwoEyes = twoEyesRadioButton.isChecked();

        //User selected a radio button for question 2
        RadioButton fiveApplesRadioButton = (RadioButton) findViewById(R.id.five_apples_radio_button);
        boolean choseFiveApples = fiveApplesRadioButton.isChecked();

        //User selected a radio button for question 3
        RadioButton noneRadioButton = (RadioButton) findViewById(R.id.none_radio_button);
        boolean choseNone = noneRadioButton.isChecked();

        //User checks nairobi for question 4
        CheckBox nairobiCheckBox = (CheckBox) findViewById(R.id.nairobi_check_box);
        boolean checkedNairobi = nairobiCheckBox.isChecked();

        //User checks lagos for question 4
        CheckBox lagosCheckBox = (CheckBox) findViewById(R.id.lagos_check_box);
        boolean checkedLagos = lagosCheckBox.isChecked();

        //User checks manhattan for question 4
        CheckBox manhattanCheckBox = (CheckBox) findViewById(R.id.manhattan_check_box);
        boolean checkedManhattan = manhattanCheckBox.isChecked();

        //User checks paris for question 4
        CheckBox parisCheckBox = (CheckBox) findViewById(R.id.paris_check_box);
        boolean checkedParis = parisCheckBox.isChecked();

        // User enters Everest sahara or some other text for question 5
        EditText everestSaharaEditTextView = (EditText) findViewById(R.id.everest_sahara_edit_text);
        String everestSahara = everestSaharaEditTextView.getText().toString().trim();

        // User enters two no or some other text for question 6
        EditText twoNoEditTextView = (EditText) findViewById(R.id.two_no_edit_text);
        String twoNo = twoNoEditTextView.getText().toString().trim();


        //Check that all questions are answered, then checks if the check boxes has only two checks
        if (checkQ1RadioButtons.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Question 1 not answered.", Toast.LENGTH_LONG).show();

        } else if (checkQ2RadioButtons.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Question 2 not answered.", Toast.LENGTH_LONG).show();

        } else if (checkQ3RadioButtons.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Question 3 not answered.", Toast.LENGTH_LONG).show();

        } else if (!checkedNairobi && !checkedLagos && !checkedManhattan&& !checkedParis) {
            Toast.makeText(this, "Question 4 not answered.", Toast.LENGTH_LONG).show();

        } else if (everestSahara.equals("")) {
            Toast.makeText(this, "Question 5 not answered.", Toast.LENGTH_LONG).show();

        } else if (twoNo.equals("")) {
            Toast.makeText(this, "Question 6 not answered.", Toast.LENGTH_LONG).show();

        } else if (nairobiCheckBox.isChecked() || lagosCheckBox.isChecked() || manhattanCheckBox.isChecked() || parisCheckBox.isChecked()) {

            //checkNrOfCheckboxesChecked method checks how many checkboxes were checked
            int finalNrOfCheckboxes = checkNrOfCheckboxesChecked(nairobiCheckBox, lagosCheckBox, manhattanCheckBox, parisCheckBox);

            // if the user checked more than 2 checkboxes, give an error toast message
            if (finalNrOfCheckboxes > 2) {

                Toast.makeText(this, "Question 4 can only have two selected answers", Toast.LENGTH_LONG).show();
            } else {

                //The calculateScoreFromRadioButtons method calculates the points from the selected radio buttons
                int finalRadioScore = calculateScoreFromRadioButtons(choseTwoEyes, choseFiveApples, choseNone);

                //The calculateScoreFromCheckBoxes method calculates the points from the checked checkboxes
                int finalCheckScore = calculateScoreFromCheckBoxes(checkedNairobi, checkedLagos);

                //The calculateScoreFromEditText method calculates the points from the Edit Views
                int finalEditScore = calculateScoreFromEditText(everestSahara, twoNo);

                //Calculate final score
                TotalScore = finalRadioScore + finalCheckScore + finalEditScore;

                if (TotalScore == 10) {
                    //You get this message if the score is equal to 10
                    Toast.makeText(this, "Congratulations." + "\nYour score is " + TotalScore + "/10", Toast.LENGTH_LONG).show();

                    //Clear all the answers to the questions
                    resetQuestions(nairobiCheckBox, lagosCheckBox, manhattanCheckBox, parisCheckBox//
                            , everestSaharaEditTextView, twoNoEditTextView);

                } else {
                    //You get this message if the score is less then 10
                    Toast.makeText(this, "Your score is " + TotalScore + "/10", Toast.LENGTH_LONG).show();

                    //Clear all the answers to the questions
                    resetQuestions(nairobiCheckBox, lagosCheckBox, manhattanCheckBox, parisCheckBox//
                            , everestSaharaEditTextView, twoNoEditTextView);
                }
            }
        }
    }

    /**
     * @param choseTwoEyes  the user selected the two eyes radiobutton
     * @param choseFiveApples the user selected the five apples radiobutton
     * @param chosenone   the user selected the none radiobutton
     * @return the score of the correct, selected radio buttons
     */
    private int calculateScoreFromRadioButtons(boolean choseTwoEyes, boolean choseFiveApples, boolean chosenone) {

        int radioScore = 0;

        // If the user chose two eyes, score increases by one
        if (choseTwoEyes) {
            radioScore += 1;
        }

        //User selects five apples, score increases by one
        if (choseFiveApples) {
            radioScore += 1;
        }

        //User selects none, score increases by one
        if (chosenone) {
            radioScore += 1;
        }
        return radioScore;
    }

    /**
     * @param checkedNairobi the user checked the nairobi checkbox
     * @param checkedLagos the user checked the lagos checkbox
     * @return the score for the correct, checked checkboxes
     */
    private int calculateScoreFromCheckBoxes(boolean checkedNairobi, boolean checkedLagos) {

        int checkScore = 0;

        //User checks nairobi, score increases by one
        if (checkedNairobi) {

            checkScore += 1;
        }

        //User checks lagos, score increases by one
        if (checkedLagos) {
            checkScore += 1;
        }
        return checkScore;
    }

    /**
     * @param everestSahara    the user typed Everest sahara as the answer
     * @param twoNo the user typed two no as the answer
     * @return the score for the correct typed answers
     */

    private int calculateScoreFromEditText(String everestSahara, String twoNo) {

        int editScore = 0;

        String q5everestSahara = "everest sahara";
        String q5EverestSahara1 = "Everest Sahara";

        //If the user types in Everest sahara, score increases by two
        if (everestSahara.toUpperCase().equals(q5everestSahara.toUpperCase()) || everestSahara.toUpperCase().equals(q5EverestSahara1.toUpperCase())) {
            editScore += 2;
        }

        String q6TwoNo = "two no";

        //If the user types in two no, score increases by three
        if (twoNo.toUpperCase().equals(q6TwoNo.toUpperCase())) {
            editScore += 3;
        }
        return editScore;
    }

    /**
     * @param nairobiCheckBox  did the user check the nairobi checkbox?
     * @param lagosCheckBox   did the user check the lagos checkbox?
     * @param manhattanCheckBox   did the user check the manhattan checkbox?
     * @param parisCheckBox did the user check the paris checkbox?
     * @return checkNoOfCheckboxes variable to determine how many check boxes were checked
     */

    private int checkNrOfCheckboxesChecked(CheckBox nairobiCheckBox, CheckBox lagosCheckBox, CheckBox manhattanCheckBox, CheckBox parisCheckBox) {

        //Adds one for every  check box checked
        int checkNoOfCheckboxes = 0;

        if (nairobiCheckBox.isChecked()) {
            checkNoOfCheckboxes += 1;
        }

        if (lagosCheckBox.isChecked()) {
            checkNoOfCheckboxes += 1;
        }

        if (manhattanCheckBox.isChecked()) {
            checkNoOfCheckboxes += 1;
        }

        if (parisCheckBox.isChecked()) {
            checkNoOfCheckboxes += 1;
        }

        //Returns the total number of checkboxes checked
        return checkNoOfCheckboxes;
    }

    /**
     * @param nairobiCheckBox         is unchecked
     * @param lagosCheckBox          is unchecked
     * @param manhattanCheckBox          is unchecked
     * @param parisCheckBox        is unchecked
     * @param everestSaharaEditTextView    is cleared
     * @param twoNoEditTextView is cleared
     */

    private void resetQuestions(CheckBox nairobiCheckBox, CheckBox lagosCheckBox, CheckBox manhattanCheckBox//
            , CheckBox parisCheckBox, EditText everestSaharaEditTextView, EditText twoNoEditTextView) {

        //Reset score to zero
        TotalScore = 0;

        //Clears all the radio groups
        RadioGroup q1RadioGroup = (RadioGroup) findViewById(R.id.q1_radio_group);
        q1RadioGroup.clearCheck();


        RadioGroup q2RadioGroup = (RadioGroup) findViewById(R.id.q2_radio_group);
        q2RadioGroup.clearCheck();

        RadioGroup q3RadioGroup = (RadioGroup) findViewById(R.id.q3_radio_group);
        q3RadioGroup.clearCheck();

        //Uncheck the checkboxes
        if (nairobiCheckBox.isChecked()) {
            nairobiCheckBox.setChecked(false);
        }

        if (lagosCheckBox.isChecked()) {
            lagosCheckBox.setChecked(false);
        }

        if (manhattanCheckBox.isChecked()) {
            manhattanCheckBox.setChecked(false);
        }

        if (parisCheckBox.isChecked()) {
            parisCheckBox.setChecked(false);
        }

        //Clears the text in the Edit Text of question 5 and returns the hint text
        everestSaharaEditTextView.setText(null);

        //Clears the text in the Edit Text of question 6 and returns the hint text
        twoNoEditTextView.setText(null);
    }
}

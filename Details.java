package com.example.buspasssystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import java.util.regex.Pattern;

public class Details1 extends AppCompatActivity {
    private EditText fullNameEditText;
    private EditText dateOfBirthEditText;
    private EditText phoneNumberEditText;
    private EditText emailEditText;
    private EditText addressEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize UI components
        fullNameEditText = findViewById(R.id.fullNameEditText);
        dateOfBirthEditText = findViewById(R.id.dateOfBirthEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        emailEditText = findViewById(R.id.emailEditText);
        addressEditText = findViewById(R.id.addressEditText);

        Button nextButton = findViewById(R.id.NextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input values
                String fullName = fullNameEditText.getText().toString().trim();
                String dateOfBirth = dateOfBirthEditText.getText().toString().trim();
                String phoneNumber = phoneNumberEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();

                // Validate input
                if (fullName.isEmpty() || dateOfBirth.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || address.isEmpty()) {
                    // Show an error message if any field is empty
                    Toast.makeText(Details1.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else if (!isValidPhoneNumber(phoneNumber)) {
                    // Check for valid phone number
                    Toast.makeText(Details1.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                } else if (!isValidDateOfBirth(dateOfBirth)) {
                    // Check for valid date of birth
                    Toast.makeText(Details1.this, "Invalid date of birth", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    // Check for valid email address
                    Toast.makeText(Details1.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                } else if (!isValidFullName(fullName)) {
                    // Check for valid full name
                    Toast.makeText(Details1.this, "Invalid full name", Toast.LENGTH_SHORT).show();
                } else {
                    // Create an Intent to start the TripRegistrationActivity
                    Intent intent = new Intent(Details1.this, TripRegistrationActivity.class);

                    // Pass user's details as extras in the Intent
                    intent.putExtra("full_name", fullName);
                    intent.putExtra("date_of_birth", dateOfBirth);
                    intent.putExtra("phone_number", phoneNumber);
                    intent.putExtra("email", email);
                    intent.putExtra("address", address);

                    startActivity(intent);
                }
            }
        });

        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign out the user (if applicable) and navigate back to the Login activity
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Details1.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear the activity stack
                startActivity(intent);
                finish(); // Close this activity
            }
        });
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Check if the phone number has 10 digits and contains only digits
        return phoneNumber.length() == 10 && phoneNumber.matches("[0-9]+");
    }

    private boolean isValidDateOfBirth(String dateOfBirth) {
        // Add your date of birth validation logic here
        // For example, you can use a regular expression to validate the date format
        // Replace the regex pattern with the format you expect
        String datePattern = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
        return dateOfBirth.matches(datePattern);
    }

    private boolean isValidEmail(String email) {
        // Use Android's built-in Patterns.EMAIL_ADDRESS to validate email
        return Pattern.matches(android.util.Patterns.EMAIL_ADDRESS.pattern(), email);
    }

    private boolean isValidFullName(String fullName) {
        // Use a regular expression pattern to match alphabetic characters only
        String namePattern = "^[a-zA-Z ]+$";  // This pattern allows alphabetic characters and spaces
        return fullName.matches(namePattern);
    }
}

package mx.edu.itson.potros.practica9

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    // Firebase Database reference
    private val userRef = FirebaseDatabase.getInstance().getReference("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializing the Save button
        val btnSave: Button = findViewById(R.id.save_button)
        btnSave.setOnClickListener { saveMarkFromForm() }

        // Adding a ChildEventListener to Firebase
        userRef.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val value = snapshot.getValue()
                if(value is String)
                {}else if(value is User){
                    val usuario=value
                    if(usuario!=null){writeMark(usuario)}
                }
            }
        })
    }

    // Function to save user data from form to Firebase
    private fun saveMarkFromForm() {
        val name: EditText = findViewById(R.id.et_name)
        val lastName: EditText = findViewById(R.id.et_lastName)
        val age: EditText = findViewById(R.id.et_age)

        val user = User(
            name.text.toString(),
            lastName.text.toString(),
            age.text.toString()
        )

        // Pushing user data to Firebase
        userRef.push().setValue(user)
    }

    // Function to write user data into the TextView
    private fun writeMark(user: User) {
        val listView: TextView = findViewById(R.id.list_textView)
        val text = "${listView.text}\n${user.toString()}"
        listView.text = text
    }
}



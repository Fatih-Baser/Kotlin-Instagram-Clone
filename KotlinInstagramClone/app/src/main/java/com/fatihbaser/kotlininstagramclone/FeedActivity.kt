package com.fatihbaser.kotlininstagramclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_feed.*
import java.sql.Timestamp

class FeedActivity : AppCompatActivity() {

    private  lateinit var auth : FirebaseAuth
    private  lateinit var db : FirebaseFirestore
    var userEmailFromFB : ArrayList<String> = ArrayList()
    var userCommentFromFB : ArrayList<String> =ArrayList()
    var userImageFromFB : ArrayList<String> =ArrayList()
    var adepter : FeedRecyclerAdapter?=null
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater =menuInflater
        menuInflater.inflate(R.menu.options_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    fun getDataFromFirestore(){
    db.collection("Posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { snapshot, exception ->

        if(exception !=null){
            Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
        }else{
            if(snapshot !=null){
                if(!snapshot.isEmpty){

                    userCommentFromFB.clear()
                    userEmailFromFB.clear()
                    userImageFromFB.clear()
                    val documents=snapshot.documents
                    for(document in documents){
                        val comment=document.get("comment") as String
                        val userEmail=document.get("userEmail") as String
                        val downloadUrl =document.get("dawnloadUrl") as String
                        val timestamp=document.get("date") as com.google.firebase.Timestamp
                        val date =timestamp.toDate()

//                        println(comment)
//                        println(userEmail)
//                        println(downloadUrl)
//                        println(date)
                        userImageFromFB.add(downloadUrl)
                        userEmailFromFB.add(userEmail)
                        userCommentFromFB.add(comment)


                        adepter!!.notifyDataSetChanged()
                    }
                }
            }
        }
    }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if(item.itemId == R.id.add_post){

            val intent=Intent(applicationContext,UploadActivity::class.java)
            startActivity(intent)

        }else if(item.itemId == R.id.logout){

            auth.signOut()
            val intent=Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        auth= FirebaseAuth.getInstance()

        db= FirebaseFirestore.getInstance()
        getDataFromFirestore()


        //recycler view option
        var layoutManger=LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManger


        adepter =FeedRecyclerAdapter(userEmailFromFB,userCommentFromFB,userImageFromFB)
        recyclerView.adapter=adepter
    }
}
package com.example.fredcrary.ad_340_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleGrid);
        mRecyclerView.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(new MyAdapter(myDataSet));
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private String[] mDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView bookTitle;
            TextView bookISBN;

            public ViewHolder(View v) {
                super(v);
                bookTitle = (TextView) v.findViewById(R.id.item_title);
                bookISBN = (TextView) v.findViewById(R.id.item_ISBN);
            }
        }

        public MyAdapter(String[] myDataSet) {
            mDataSet = myDataSet;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            // Create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_item_row, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bookTitle.setText(mDataSet[2*position]);
            holder.bookISBN.setText(mDataSet[2*position+1]);
        }

        // Return the size of the dataset
        @Override
        public int getItemCount() {
            return mDataSet.length / 2;
        }
    }

    String[] myDataSet = {
        "25 Things to Do", "978-1-884734-53-3",
        "All My Feelings at Preschool: Nathan's Day", "978-0-943990-60-6",
        "Am I Doing Too Much for My Child?", "978-1-884734-96-0",
        "The Biting Solution", "978-1-936903-07-8",
        "I Can't Wait", "978-1-884734-22-9",
        "I Want It", "978-1-884734-14-4",
        "I Want to Play", "978-1-884734-18-2",
        "I'm Lost", "978-1-884734-24-3",
        "Mommy, Don't Go", "978-1-884734-20-5",
        "My Name is Not Dummy", "978-1-884734-16-8",
        "Dealing with Disappointment", "978-1-884734-75-5",
        "I'm Excited", "978-0-943990-91-0",
        "I'm Frustrated", "978-0-943990-64-4",
        "I'm Furious", "978-0-943990-93-4",
        "I'm Mad", "978-0-943990-62-0",
        "I'm Proud", "978-0-943990-66-8",
        "I'm Scared", "978-0-943990-89-7",
        "Bully on the Bus", "978-0-943990-42-2",
        "Finders, Keepers?", "978-0-943990-38-5",
        "First Day Blues", "978-0-943990-72-9",
        "Making the Grade", "978-0-943990-48-4",
        "Under Whose Influence?", "978-0-943990-97-2",
        "Feeling Elf Cards", "978-1-884734-80-9",
        "Grounded for Life?!", "978-0-943990-95-8",
        "Help! The Kids Are at It Again", "978-1-884734-08-3",
        "Is This a Phase?", "978-1-884734-63-2",
        "It's MY Body", "978-0-943990-03-3",
        "Amy's Disappearing Pickle", "978-1-884734-59-5",
        "Heidi's Irresistible Hat", "978-1-884734-55-7",
        "Willy's Noisy Sister", "978-1-884734-57-1",
        "Kids to the Rescue!", "978-1-884734-78-6",
        "Living with the Active Alert Child", "978-1-884734-77-9",
        "Love & Limits", "978-1-884734-04-5",
        "Loving Touches", "978-0-943990-20-0",
        "Mommy! I Have to Go Potty!", "978-0-9650477-1-5",
        "My Grandma Died", "978-1-884734-26-7",
        "Mudras", "978-0-943990-40-8",
        "On the Wings of a Butterfly", "978-0-943990-68-2",
        "Out of Harm's Way", "978-1-884734-97-7",
        "Pick Up Your Socks", "978-0-943990-52-1",
        "Redirecting Children's Behavior", "978-1-884734-30-4",
        "Safe Connections", "978-1-936903-00-9",
        "Self-Calming Cards", "978-1-884734-67-0",
        "The Sleep Book for Tired Parents", "978-0-943990-34-7",
        "Something Happened", "978-0-943990-28-6",
        "Something Is Wrong at My House", "978-1-884734-65-6",
        "STAR Parenting Tales and Tools", "978-1-884734-95-3",
        "Taking \"No\" for an Answer", "978-1-884734-44-1",
        "Telling Isn't Tattling", "978-1-884734-06-9",
        "Temperament Tools", "978-1-936903-25-2",
        "Time-In", "978-1-884734-28-1",
        "365 Wacky, Wonderful Ways", "978-0-943990-79-8",
        "Magic Tools for Raising Kids", "978-0-943990-77-4",
        "Taking Care of Me", "978-1-884734-02-1",
        "The Trouble with Secrets", "978-0-943990-22-4",
        "Using Your Values", "1-884734-36-7",
        "The Way I Act", "978-1-884734-99-1",
        "The Way I Feel", "978-1-884734-71-7",
        "The Way I Feel", "978-1-884734-72-4",
        "What About Me?", "978-1-884734-86-1",
        "What Am I Feeling?", "978-1-884734-52-6",
        "What Angry Kids Need", "978-1-884734-84-7",
        "What Is a Feeling?", "978-0-943990-75-0",
        "When You're Happy", "978-1-884734-12-0",
        "When You're Mad", "978-1-884734-10-6",
        "When You're Shy", "978-1-884734-11-3",
        "When You're Silly", "978-1-884734-13-7",
        "Where Is My Mommy?", "978-1-884734-46-5",
        "Who, Me Lead a Group?", "978-1-936903-29-0",
        "Why Don't You Understand?", "978-1-884734-68-7",
        "Why Does that Man Have Such a BIG NOSE?", "978-0-943990-24-8",
        "Without Spanking or Spoiling", "978-0-943990-74-3"
    };
}

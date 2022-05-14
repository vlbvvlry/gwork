package com.example.iem.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iem.MainActivity;
import com.example.iem.R;
import com.example.iem.network.NetworkService;
import com.example.iem.network.models.TaskList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TasksFragment extends Fragment {

    private final List<CheckBox> taskStateList = new ArrayList<>();
    private ListView taskList = null;
    private List<TaskList> taskListList = new ArrayList<>();


    public TasksFragment() {
        super(R.layout.fragment_tasks);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        debugInit();
        taskListInit(view);
        taskListCheckButtonInit(view);
        linkListInit(view);
        return view;
    }

    private void loadTask() {
        (new NetworkService())
                .getApi()
                .getAllTaskList(MainActivity.MyUser.id)
                .enqueue(new Callback<List<TaskList>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<TaskList>> call, @NonNull Response<List<TaskList>> response) {
                        taskListList = response.body();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<TaskList>> call, @NonNull Throwable t) {
                        System.out.println("TASK LIST GETTING FAILED");
                    }
                });
    }

    private void linkListInit(View view) {
        List<String> linkStateList = new ArrayList<String>();
        linkStateList.add("https://vk.com/audios137190618");
        linkStateList.add("https://android-tools.ru/help/chetyre-sposoba-dobavit-ssylku-v-razmetku/");
        linkStateList.add("http://rusdelphi.com/tag/android/");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), R.layout.link_item, R.id.linkItem, linkStateList);
        ListView lv = view.findViewById(R.id.linkList);
        lv.setAdapter(adapter);
    }

    private void taskListInit(View view) {
        ArrayAdapter<CheckBox> adapter = new ArrayAdapter<CheckBox>(requireContext(), R.layout.task_item, taskStateList) {
            @SuppressLint("ViewHolder")
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater inflater = ((Activity)requireContext()).getLayoutInflater();
                convertView = inflater.inflate(R.layout.task_item, parent,false);
                CheckBox cb= convertView.findViewById(R.id.radioTask);
                cb.setText(taskStateList.get(position).getText());
                cb.setChecked(taskStateList.get(position).isChecked());
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        taskList.setItemChecked(position, cb.isChecked());
                        if (cb.isChecked()) {
                            SpannableString str = new SpannableString(cb.getText());
                            str.setSpan(new StrikethroughSpan(), 0, str.length(), 0);
                            cb.setText(str);
                        } else {
                            String str = cb.getText().toString();
                            cb.setText(str);
                        }
                        taskStateList.set(position, cb);
                    }
                });
                return convertView;
            }
        };
        adapter.notifyDataSetChanged();
        taskList = view.findViewById(R.id.task_list);
        taskList.setItemsCanFocus(false);
        taskList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        taskList.setSelected(true);
        taskList.setClickable(true);
        taskList.setAdapter(adapter);
    }

    private void taskListCheckButtonInit(View view) {
        view.findViewById(R.id.checkBoxButton)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray sba = taskList.getCheckedItemPositions();
                createTaskListDialog(requireActivity());
            }
        });
    }

    private void createTaskListDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Выполненные задачи")
                .setMessage(taskList.getCheckedItemPositions().toString())
                .setPositiveButton("Отправить куратору", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(activity, "Click Ok", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.create().show();
    }

    private void debugInit() {
        for(int i = 0; i < 3; i++) {
            String str = "Задача";
            CheckBox ncb = new CheckBox(requireContext());
            ncb.setText(str);
            taskStateList.add(ncb);
        }
    }

}

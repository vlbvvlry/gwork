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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.iem.MainActivity;
import com.example.iem.R;
import com.example.iem.network.NetworkService;
import com.example.iem.network.models.Task;
import com.example.iem.network.models.TaskList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Lvl1TasksFragment extends Fragment {

    private final List<CheckBox> taskStateList = new ArrayList<>();
    private ListView taskList = null;
    private List<TaskList> taskListList = null;


    public Lvl1TasksFragment() {
        super(R.layout.fragment_tasks_lvl1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks_lvl1, container, false);
        taskListInit(view);
        taskListCheckButtonInit(view);
        linkListInit(view);
        loadTaskBox(view);
        return view;
    }

    private void loadTaskBox(View view) {
        (new NetworkService())
                .getApi()
                .getAllTaskList(MainActivity.MyUser.id)
                .enqueue(new Callback<List<TaskList>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<TaskList>> call, @NonNull Response<List<TaskList>> response) {
                        if (response.body() == null || response.body().size() < 1) {
                            ((TextView)view.findViewById(R.id.taskListBoxTitle))
                                    .setText("Задач нет");
                            return;
                        }
                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                        taskListList = response.body();
                        loadTaskList(view, taskListList.get(0).getId());
                        ((TextView)view.findViewById(R.id.taskListBoxTitle))
                                .setText(taskListList.get(0).getTitle());
                        ((TextView)view.findViewById(R.id.DL))
                                .setText(sdf.format(taskListList.get(0).getDeadline()));
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<TaskList>> call, @NonNull Throwable t) {
                        System.out.println("TASK LIST GETTING FAILED");
                    }
                });
    }

    private void loadTaskList(View view, Integer taskListId) {
        (new NetworkService())
                .getApi()
                .getAllTaskByTaskList(taskListId)
                .enqueue(new Callback<List<Task>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Task>> call, @NonNull Response<List<Task>> response) {
                        List<Task> body = response.body();
                        if(body == null || body.size() < 1) {
                            return;
                        }
                        for (int i = 0; i < body.size(); i++) {
                            CheckBox cb = new CheckBox(requireContext());
                            cb.setText(body.get(i).getContent());
                            cb.setChecked(body.get(i).getChecked());
                            taskStateList.add(cb);
                            taskListInit(view);
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<Task>> call, @NonNull Throwable t) {
                        System.out.println("err");
                    }
                });
    }

    private void linkListInit(View view) {
        List<String> linkStateList = new ArrayList<String>();
        linkStateList.add("https://vk.com/audios137190618");
        linkStateList.add("https://android-tools.ru/help/chetyre-sposoba-dobavit-ssylku-v-razmetku/");
        linkStateList.add("http://rusdelphi.com/tag/android/");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), R.layout.item_link, R.id.linkItem, linkStateList);
        ListView lv = view.findViewById(R.id.linkList);
        lv.setAdapter(adapter);
    }

    private void taskListInit(View view) {
        ArrayAdapter<CheckBox> adapter = getCheckBoxAdapter();
        adapter.notifyDataSetChanged();
        taskList = view.findViewById(R.id.task_list);
        taskList.setItemsCanFocus(false);
        taskList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        taskList.setSelected(true);
        taskList.setClickable(true);
        taskList.setAdapter(adapter);
    }

    private ArrayAdapter<CheckBox> getCheckBoxAdapter() {
        ArrayAdapter<CheckBox> adapter = new ArrayAdapter<CheckBox>(requireContext(), R.layout.item_task, taskStateList) {
            @SuppressLint("ViewHolder")
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater inflater = ((Activity)requireContext()).getLayoutInflater();
                convertView = inflater.inflate(R.layout.item_task, parent,false);
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
        return adapter;
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

}

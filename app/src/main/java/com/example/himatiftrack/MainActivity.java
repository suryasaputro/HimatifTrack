package com.example.himatiftrack;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import android.view.animation.LayoutAnimationController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    private FrameLayout container;
    private DatabaseHelper dbHelper;
    private List<Mahasiswa> listMahasiswa = new ArrayList<>();
    private Typeface comfortaa, comfortaaBold;

    private enum Screen {SPLASH, MENU, FORM, LIST, DETAIL}
    private Screen currentScreen = Screen.MENU;
    private Screen previousScreen = Screen.MENU;
    private Mahasiswa mahasiswaTerpilih = null;

    private boolean isFirstAfterSplash = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.main_container);
        comfortaa = ResourcesCompat.getFont(this, R.font.comfortaa_regular);
        comfortaaBold = ResourcesCompat.getFont(this, R.font.comfortaa_bold);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.himatif_background));
        }

        dbHelper = new DatabaseHelper(this);
        showSplash();
    }

    private void showSplash() {
        setPreviousScreen();
        currentScreen = Screen.SPLASH;
        View splashView = LayoutInflater.from(this).inflate(R.layout.layout_splash, container, false);
        replaceViewWithFade(splashView);
        splashView.postDelayed(this::showMenu, 2000);
    }

    private void showMenu() {
        setPreviousScreen();
        currentScreen = Screen.MENU;
        View menuView = LayoutInflater.from(this).inflate(R.layout.layout_menu, container, false);
        replaceViewWithZoom(menuView);

        TextView tvMainTitle = menuView.findViewById(R.id.tvMainTitle);
        tvMainTitle.setTypeface(comfortaaBold);

        Button btnInput = menuView.findViewById(R.id.btnInputData);
        Button btnLihat = menuView.findViewById(R.id.btnLihatData);
        btnInput.setTypeface(comfortaa);
        btnLihat.setTypeface(comfortaa);

        btnInput.setOnClickListener(v -> showForm(null));
        btnLihat.setOnClickListener(v -> showList());
    }

    private void showForm(Mahasiswa data) {
        setPreviousScreen();
        currentScreen = Screen.FORM;
        View formView = LayoutInflater.from(this).inflate(R.layout.layout_form, container, false);
        replaceViewWithZoom(formView);

        TextView tvFormTitle = formView.findViewById(R.id.tvMainTitle);
        tvFormTitle.setText(data != null ? "Ubah Data Mahasiswa" : "Input Data Mahasiswa");
        tvFormTitle.setTypeface(comfortaaBold);

        EditText etNama = formView.findViewById(R.id.editNama);
        EditText etNim = formView.findViewById(R.id.editNIM);
        MaterialAutoCompleteTextView spinnerAngkatan = formView.findViewById(R.id.dropdownAngkatan);
        EditText etEmail = formView.findViewById(R.id.editEmail);
        MaterialAutoCompleteTextView dropdownGender = formView.findViewById(R.id.dropdownGender);
        EditText etTempat = formView.findViewById(R.id.editTempatLahir);
        EditText etTanggal = formView.findViewById(R.id.editTanggalLahir);
        EditText etHobi = formView.findViewById(R.id.editHobi);
        EditText etAlamat = formView.findViewById(R.id.editAlamat);
        Button btnSimpan = formView.findViewById(R.id.btnSimpan);

        etTanggal.setFocusable(false);
        etTanggal.setClickable(true);
        etTanggal.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this,
                    (view, year, month, day) -> {
                        etTanggal.setText(day + "/" + (month + 1) + "/" + year);
                        etHobi.requestFocus();
                    },
                    cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });

        EditText[] fields = {etNama, etNim, etEmail, etTempat, etTanggal, etHobi, etAlamat};
        for (EditText e : fields) e.setTypeface(comfortaa);
        spinnerAngkatan.setTypeface(comfortaa);
        dropdownGender.setTypeface(comfortaa);
        btnSimpan.setTypeface(comfortaaBold);

        setupDropdowns(spinnerAngkatan, dropdownGender);

        etNama.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        etNama.setInputType(InputType.TYPE_CLASS_TEXT);
        etNama.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                etNim.requestFocus();
                return true;
            }
            return false;
        });

        etNim.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        etNim.setInputType(InputType.TYPE_CLASS_NUMBER);
        etNim.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                spinnerAngkatan.requestFocus();
                spinnerAngkatan.showDropDown();
                return true;
            }
            return false;
        });

        spinnerAngkatan.setInputType(InputType.TYPE_NULL);
        spinnerAngkatan.setOnItemClickListener((parent, view, position, id) -> {
            etEmail.requestFocus();
        });

        etEmail.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        etEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        etEmail.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                dropdownGender.requestFocus();
                dropdownGender.showDropDown();
                return true;
            }
            return false;
        });

        dropdownGender.setInputType(InputType.TYPE_NULL);
        dropdownGender.setOnItemClickListener((parent, view, position, id) -> {
            etTempat.requestFocus();
        });

        etTempat.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        etTempat.setInputType(InputType.TYPE_CLASS_TEXT);
        etTempat.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                etTanggal.requestFocus();
                etTanggal.performClick();
                return true;
            }
            return false;
        });

        etHobi.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        etHobi.setInputType(InputType.TYPE_CLASS_TEXT);
        etHobi.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                etAlamat.requestFocus();
                return true;
            }
            return false;
        });

        etAlamat.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etAlamat.setRawInputType(InputType.TYPE_CLASS_TEXT);

        if (data != null) {
            etNama.setText(data.getNama());
            etNim.setText(data.getNim());
            spinnerAngkatan.setText(data.getAngkatan(), false);
            etEmail.setText(data.getEmail());
            dropdownGender.setText(data.getGender(), false);
            etTempat.setText(data.getTempatLahir());
            etTanggal.setText(data.getTanggalLahir());
            etAlamat.setText(data.getAlamat());
            etHobi.setText(data.getHobi());
        }

        btnSimpan.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etNama.getText()) || TextUtils.isEmpty(etNim.getText())) {
                showToast("Nama & NIM wajib diisi!"); return;
            }
            if (!etNim.getText().toString().matches("\\d+")) {
                showToast("NIM harus berupa angka!"); return;
            }
            if (!etEmail.getText().toString().contains("@")) {
                showToast("Email tidak valid!"); return;
            }
            if (TextUtils.isEmpty(dropdownGender.getText())) {
                showToast("Pilih jenis kelamin!"); return;
            }
            if (spinnerAngkatan.getText().toString().trim().isEmpty()) {
                showToast("Silakan pilih tahun angkatan!"); return;
            }

            Mahasiswa m = new Mahasiswa(
                    data != null ? data.getId() : 0,
                    etNama.getText().toString(),
                    etNim.getText().toString(),
                    spinnerAngkatan.getText().toString(),
                    etEmail.getText().toString(),
                    etTempat.getText().toString(),
                    etTanggal.getText().toString(),
                    dropdownGender.getText().toString(),
                    etAlamat.getText().toString(),
                    etHobi.getText().toString()
            );

            if (data != null) {
                dbHelper.updateMahasiswa(m);
                showToast("Data berhasil diperbarui");
                showDetail(m);
            } else {
                dbHelper.insertMahasiswa(m);
                showToast("Data berhasil disimpan");
                showList();
            }
        });
    }

    private void showList() {
        setPreviousScreen();
        currentScreen = Screen.LIST;

        View listView = LayoutInflater.from(this).inflate(R.layout.layout_list, container, false);
        replaceViewWithZoom(listView);

        // Inisialisasi RecyclerView
        RecyclerView recyclerView = listView.findViewById(R.id.recyclerMahasiswa);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        listMahasiswa = dbHelper.getAllMahasiswa();
        MahasiswaAdapter adapter = new MahasiswaAdapter(this, listMahasiswa, this::showDetail);
        recyclerView.setAdapter(adapter);

        // Animasi smooth item list
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_zoom_in);
        recyclerView.setLayoutAnimation(animationController);
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(() -> recyclerView.scheduleLayoutAnimation());

        // 🎯 Tambahkan animasi khusus untuk header agar smooth
        View header = listView.findViewById(R.id.headerContainer);
        if (header != null) {
            Animation headerAnim = AnimationUtils.loadAnimation(this, R.anim.fade_zoom_in);
            headerAnim.setStartOffset(20); // Delay agar tidak bentrok dengan zoom container
            header.startAnimation(headerAnim);
        }
    }



    private void showDetail(Mahasiswa m) {
        setPreviousScreen();
        currentScreen = Screen.DETAIL;
        mahasiswaTerpilih = m;
        View detailView = LayoutInflater.from(this).inflate(R.layout.layout_detail, container, false);
        replaceViewWithZoom(detailView);

        ((TextView) detailView.findViewById(R.id.tvHeaderNama)).setText(m.getNama());
        ((TextView) detailView.findViewById(R.id.tvHeaderNIM)).setText(m.getNim());
        ((TextView) detailView.findViewById(R.id.tvEmail)).setText(m.getEmail());
        ((TextView) detailView.findViewById(R.id.tvTTL)).setText(m.getTempatLahir() + ", " + m.getTanggalLahir());
        ((TextView) detailView.findViewById(R.id.tvGender)).setText(m.getGender());
        ((TextView) detailView.findViewById(R.id.tvAngkatan)).setText(m.getAngkatan());
        ((TextView) detailView.findViewById(R.id.tvHobi)).setText(m.getHobi());
        ((TextView) detailView.findViewById(R.id.tvAlamat)).setText(m.getAlamat());

        detailView.findViewById(R.id.btnEdit).setOnClickListener(v -> showForm(m));
        detailView.findViewById(R.id.btnHapus).setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Konfirmasi Hapus")
                    .setMessage("Yakin ingin menghapus data \"" + m.getNama() + "\"?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        dbHelper.deleteMahasiswa(m.getId());
                        showToast("Data dihapus");
                        showList();
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });
    }

    private void replaceViewWithZoom(View newView) {
        if (isFirstAfterSplash) {
            isFirstAfterSplash = false;
            replaceViewWithFade(newView);
            return;
        }

        if (container.getChildCount() > 0) {
            View current = container.getChildAt(0);
            Animation zoomOut = AnimationUtils.loadAnimation(this, R.anim.zoom_out);
            zoomOut.setAnimationListener(new Animation.AnimationListener() {
                @Override public void onAnimationStart(Animation animation) {}
                @Override public void onAnimationRepeat(Animation animation) {}
                @Override public void onAnimationEnd(Animation animation) {
                    container.removeAllViews();
                    container.addView(newView);
                    newView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in));
                }
            });
            current.startAnimation(zoomOut);
        } else {
            container.removeAllViews();
            container.addView(newView);
            newView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in));
        }
    }

    private void replaceViewWithFade(View newView) {
        container.removeAllViews();
        container.addView(newView);
        newView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
    }


    private void setupDropdowns(MaterialAutoCompleteTextView angkatan, MaterialAutoCompleteTextView gender) {
        String[] tahun = {"2023", "2024", "2025"};
        String[] genders = {"Laki-laki", "Perempuan"};
        angkatan.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, tahun));
        gender.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, genders));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setPreviousScreen() {
        previousScreen = currentScreen;
    }

    @Override
    public void onBackPressed() {
        switch (currentScreen) {
            case DETAIL: showList(); break;
            case FORM:
                if (previousScreen == Screen.DETAIL && mahasiswaTerpilih != null) showDetail(mahasiswaTerpilih);
                else showMenu(); break;
            case LIST: showMenu(); break;
            case MENU:
            default: super.onBackPressed(); break;
        }
    }
}

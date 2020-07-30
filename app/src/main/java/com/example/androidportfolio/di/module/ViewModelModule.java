package com.example.androidportfolio.di.module;

        import androidx.lifecycle.ViewModel;
        import androidx.lifecycle.ViewModelProvider;

        import com.example.androidportfolio.di.ViewModelFactory;
        import com.example.androidportfolio.di.ViewModelKey;
        import com.example.androidportfolio.ui.sunshine.viewmodel.SunshineViewModel;

        import dagger.Binds;
        import dagger.Module;
        import dagger.multibindings.IntoMap;

        @Module
        public abstract class ViewModelModule {

        @Binds
        @IntoMap
        @ViewModelKey(SunshineViewModel.class)
        abstract ViewModel bindViewModel(SunshineViewModel sunshineViewModel);

        @Binds
        abstract ViewModelProvider.Factory bindFactory(ViewModelFactory factory);
        }

package com.example.jokesapp2.categories;

public interface CategoryContract {

    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * setDataToRecyclerView and onResponseFailure is fetched from the CategoryInteractor class
     * */
    interface View {
        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(String[] categories);

        void onResponseFailure(Throwable throwable);
    }

    /**
     * Call when user interact with the view
     * */
    interface Presenter {

        void requestDataFromServer();
    }

    /**
     * Interactors are classes built for fetching data from your database, web services, or any other data source.
     **/
    interface Interactor {

        interface OnFinishedListener{
            void onFinished(String[] categories);

            void onFailure(Throwable throwable);
        }

        void getCategoriesArrayList(OnFinishedListener onFinishedListener);
    }
}

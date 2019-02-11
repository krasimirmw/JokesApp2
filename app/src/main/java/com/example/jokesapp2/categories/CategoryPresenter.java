package com.example.jokesapp2.categories;

/* CategoryPresenter is responsible for querying the model and updating the CategoryActivity view */
public class CategoryPresenter implements CategoryContract.Presenter, CategoryContract.Interactor.OnFinishedListener {


    // View instance used for displaying data on the View
    private CategoryContract.View view;

    // Interactor used for querying the data via Retrofit
    private CategoryContract.Interactor interactor;

    public CategoryPresenter(CategoryContract.View view, CategoryContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void requestDataFromServer() {
        // Requests data from server via interactor
        interactor.getCategoriesArrayList(this);
    }

    @Override
    public void onFinished(String[] categories) {
        // Sets the data to RecyclerView and hides progressbar
        if (view !=null) {
            view.setDataToRecyclerView(categories);
            view.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        // Shows reason for error and hides progressbar
        if (view !=null) {
            view.onResponseFailure(throwable);
            view.hideProgress();
        }
    }
}

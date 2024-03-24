package ru.senla;

@Singleton
@Deprecated
public class RecommendatorImpl implements Recommendator {

    public RecommendatorImpl() {
        System.out.println("recommendator was creator");
    }

    @InjectProperty("wisky")
    //@InjectProperty // если alcohol, совпадает с названием в файле application.properties,
    // то можно ничего не писать в аргументах аннотации @InjectProperty либо @InjectProperty("alcohol")
    private String alcohol;

    @Override
    public void recomend() {
        System.out.println("to protect from covid-2019,  drink " + alcohol);
    }
}

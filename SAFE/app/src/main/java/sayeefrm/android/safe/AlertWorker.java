package sayeefrm.android.safe;

import android.content.Context;
import android.support.annotation.NonNull;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class AlertWorker extends Worker {

    public AlertWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Worker.Result doWork() {
        // Listen for tripped readings from active raspberry pi's here

        return Result.SUCCESS;
    }

}


package me.spark.mvvm.binding.command;


import io.reactivex.functions.Function;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/2
 * 描    述：执行的命令事件转换
 * 修订历史：
 * ================================================
 */
public class ResponseCommand<T, R> {

    private BindingFunction<R> execute;
    private Function<T, R> function;
    private BindingFunction<Boolean> canExecute;

    /**
     * like {@link BindingCommand},but ResponseCommand can return result when command has executed!
     *
     * @param execute function to execute when event occur.
     */
    public ResponseCommand(BindingFunction<R> execute) {
        this.execute = execute;
    }


    public ResponseCommand(Function<T, R> execute) {
        this.function = execute;
    }


    public ResponseCommand(BindingFunction<R> execute, BindingFunction<Boolean> canExecute) {
        this.execute = execute;
        this.canExecute = canExecute;
    }


    public ResponseCommand(Function<T, R> execute, BindingFunction<Boolean> canExecute) {
        this.function = execute;
        this.canExecute = canExecute;
    }


    public R execute() {
        if (execute != null && canExecute()) {
            return execute.call();
        }
        return null;
    }

    private boolean canExecute() {
        if (canExecute == null) {
            return true;
        }
        return canExecute.call();
    }


    public R execute(T parameter) throws Exception {
        if (function != null && canExecute()) {
            return function.apply(parameter);
        }
        return null;
    }
}

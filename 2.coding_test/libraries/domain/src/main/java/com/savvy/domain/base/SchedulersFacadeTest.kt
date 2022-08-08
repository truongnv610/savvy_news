package com.savvy.domain.base

import com.savvy.domain.SchedulersFacade
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class SchedulersFacadeTest : SchedulersFacade() {
    override val io: Scheduler
        get() = Schedulers.trampoline()

    override val computation: Scheduler
        get() = Schedulers.trampoline()

    override val ui: Scheduler
        get() = Schedulers.trampoline()
}
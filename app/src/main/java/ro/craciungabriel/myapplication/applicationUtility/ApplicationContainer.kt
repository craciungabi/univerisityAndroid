package ro.craciungabriel.myapplication.applicationUtility

import ro.craciungabriel.myapplication.repository.EmployeeRepositoryInMemory;
import ro.craciungabriel.myapplication.repository.Repository;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import javax.inject.Singleton;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent::class)
class ApplicationContainer {

    companion object {
        private var employeeRepository: Repository? = null
    }

    fun getSingletonEmployeeRepository(): Repository? {
        if (employeeRepository == null) {
            employeeRepository = EmployeeRepositoryInMemory()
        }

        return employeeRepository
    }

    @Provides
    @Singleton
    fun provideApparelRepository(repository: EmployeeRepositoryInMemory): Repository {
        return EmployeeRepositoryInMemory()
    }
}
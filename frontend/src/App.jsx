import React from 'react';
import AccountCreation from './components/AccountCreation';
import AccountInfo from './components/AccountInfo';

const App = () => {
  return (
    <div className="container mx-auto p-4">
      <h1 className="text-3xl font-bold mb-8">Banking Application</h1>
      
      <div className="space-y-6">
        <div className="bg-white shadow rounded-lg p-6">
          <h2 className="text-xl font-bold mb-4">Create New Account</h2>
          <AccountCreation />
        </div>

        <div className="bg-white shadow rounded-lg p-6">
          <h2 className="text-xl font-bold mb-4">Account Information</h2>
          <AccountInfo />
        </div>
      </div>
    </div>
  );
};

export default App;
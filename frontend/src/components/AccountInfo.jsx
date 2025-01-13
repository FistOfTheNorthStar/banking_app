import React, { useState } from 'react';

const AccountInfo = () => {
  const [userId, setUserId] = useState('');
  const [accountInfo, setAccountInfo] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchAccountInfo = async () => {
    setLoading(true);
    setError(null);
    
    try {
      const response = await fetch(`http://localhost:8072/api/users/${userId}/account-info`);
      if (!response.ok) {
        throw new Error('Failed to fetch account information');
      }
      
      const data = await response.json();
      setAccountInfo(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    fetchAccountInfo();
  };

  return (
    <div>
      <form onSubmit={handleSubmit} className="mb-6">
        <div className="flex gap-4">
          <input
            type="text"
            value={userId}
            onChange={(e) => setUserId(e.target.value)}
            className="flex-1 px-3 py-2 border rounded-md"
            placeholder="Enter User ID"
            required
          />
          <button
            type="submit"
            disabled={loading}
            className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 disabled:bg-blue-300"
          >
            {loading ? 'Loading...' : 'Get Info'}
          </button>
        </div>
      </form>

      {error && (
        <div className="mb-4 p-3 bg-red-100 text-red-700 rounded-md">
          {error}
        </div>
      )}

      {accountInfo && (
        <div className="space-y-6">
          <div className="bg-gray-50 p-4 rounded-md">
            <h3 className="text-lg font-bold mb-2">Customer Details</h3>
            <div className="space-y-1">
              <p><span className="font-medium">Name:</span> {accountInfo.name}</p>
              <p><span className="font-medium">Surname:</span> {accountInfo.surname}</p>
              <p><span className="font-medium">Balance:</span> ${accountInfo.balance}</p>
            </div>
          </div>

          <div>
            <h3 className="text-lg font-bold mb-2">Transactions</h3>
            {accountInfo.transactions && accountInfo.transactions.length > 0 ? (
              <div className="space-y-2">
                {accountInfo.transactions.map((transaction) => (
                  <div key={transaction.id} className="bg-gray-50 p-3 rounded-md">
                    <p><span className="font-medium">Amount:</span> ${transaction.amount}</p>
                    <p><span className="font-medium">Type:</span> {transaction.type}</p>
                    <p><span className="font-medium">Date:</span> {new Date(transaction.timestamp).toLocaleString()}</p>
                  </div>
                ))}
              </div>
            ) : (
              <p className="text-gray-500">No transactions found</p>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default AccountInfo;
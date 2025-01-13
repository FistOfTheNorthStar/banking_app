import React, { useState } from 'react';

const AccountCreation = () => {
  const [customerId, setCustomerId] = useState('');
  const [initialCredit, setInitialCredit] = useState('0');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setSuccess(false);

    try {
      const response = await fetch(`http://localhost:8070/api/accounts?customerId=${customerId}&initialCredit=${initialCredit}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        }
      });

      if (!response.ok) {
        throw new Error('Failed to create account');
      }

      setSuccess(true);
      setCustomerId('');
      setInitialCredit('0');
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block text-sm font-medium mb-1">
            Customer ID
          </label>
          <input
            type="text"
            value={customerId}
            onChange={(e) => setCustomerId(e.target.value)}
            className="w-full px-3 py-2 border rounded-md"
            required
          />
        </div>

        <div>
          <label className="block text-sm font-medium mb-1">
            Initial Credit
          </label>
          <input
            type="number"
            value={initialCredit}
            onChange={(e) => setInitialCredit(e.target.value)}
            className="w-full px-3 py-2 border rounded-md"
            min="0"
            step="0.01"
          />
        </div>

        <button
          type="submit"
          disabled={loading}
          className="w-full bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 disabled:bg-blue-300"
        >
          {loading ? 'Creating...' : 'Create Account'}
        </button>
      </form>

      {error && (
        <div className="mt-4 p-3 bg-red-100 text-red-700 rounded-md">
          {error}
        </div>
      )}

      {success && (
        <div className="mt-4 p-3 bg-green-100 text-green-700 rounded-md">
          Account created successfully!
        </div>
      )}
    </div>
  );
};

export default AccountCreation;
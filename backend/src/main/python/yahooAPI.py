import numpy as np
import pandas as pd 
import yfinance as yf
from datetime import datetime, timedelta
import os

def fetchdata(ticker, start, end=None, interval="1d"):
    try:
        if end is None:
            end = datetime.now()

        print("Start date is: " + start)
        print("End date is: " + end)

        # Download data
        data = yf.download(
            ticker, 
            start=pd.to_datetime(start), 
            end=pd.to_datetime(end), 
            interval=interval
        )
        
        # Return empty list if no data
        if data.empty:
            return []
            
        # Convert to dictionaries
        return data.reset_index().to_dict(orient='records')
        
    except Exception as e:
        print(f"Error fetching data for {ticker}: {str(e)}")
        return []

if __name__ == "__main__":
    pass

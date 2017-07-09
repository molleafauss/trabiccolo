"""
Simple script to exercise the endpoint. Will send random valid message with some invalid messages.
Uses standard python 2.7 library, no extra sugar needed.
"""
import argparse
import urllib2
import time
import random
import json
import threading
import logging
import sys

COUNTRIES = ["IE", "IT", "UK", "DE", "US", "FR", "ES", "PT", "RU"]
CURRENCIES = ["EUR", "USD", "GBP", "JPY", "SEK"]


def parse_args():
    p = argparse.ArgumentParser()
    p.add_argument("--endpoint", default="http://localhost:8080/message",
                   help="Endpoint to call. Default to http://localhost:8080/message")
    p.add_argument("--sleep", type=int, default=500,
                   help="Sleep time in ms between messages sent. Defaults to 500ms")
    p.add_argument("--threads", type=int, default=1,
                   help="Can be used to specify to start multiple threads")
    p.add_argument("--broken-chance", type=int, default=100,
                   help="""Chance to have a broken message (i.e. invalid). Defaults 1 in X where X the value of this parameter. 
                   Lower parameter means more frequent broken messages.""")
    return p.parse_args()


def make_message(args):
    message = {
        "userId": "123456",
        "currencyFrom": random.choice(CURRENCIES),
        "currencyTo": random.choice(CURRENCIES),
        "amountSell": 1000,
        "amountBuy": 800,
        "rate": 0.8,
        "timePlaced": time.strftime("%d-%b-%y %H:%M:%S"),
        "originatingCountry": random.choice(COUNTRIES)
    }
    headers = {
        "Content-Type": "application/json"
    }
    return message, headers


def post_messages(i, args):
    while True:
        message, headers = make_message(args)
        r = urllib2.Request(args.endpoint, json.dumps(message), headers=headers)
        f = urllib2.urlopen(r)
        logging.info("%d, %s", i, f.getcode())
        time.sleep(args.sleep / 1000.0)


def run(args):
    for i in range(args.threads):
        threading.Thread(target=post_messages, args=(i, args)).start()


if __name__ == '__main__':
    logging.basicConfig(level=logging.INFO, stream=sys.stdout)
    args = parse_args()
    run(args)